package com.bst.gitlab.task;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bst.gitlab.common.utils.DateUtilEx;
import com.bst.gitlab.dao.GitCodeCountMapper;
import com.bst.gitlab.dao.GitProjectMapper;
import com.bst.gitlab.dao.GitUserMapper;
import com.bst.gitlab.dao.model.GitCodeCount;
import com.bst.gitlab.dao.model.GitProject;
import com.bst.gitlab.dao.model.GitUser;
import com.bst.gitlab.gitlabapi.GitlabApiClient;
import com.bst.gitlab.gitlabapi.models.req.*;
import com.bst.gitlab.gitlabapi.models.res.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Log4j2
public class GitLabWorkTask {

    @Autowired
    private GitUserMapper gitUserMapper;
    @Autowired
    private GitProjectMapper gitProjectMapper;
    @Autowired
    private GitCodeCountMapper gitCodeCountMapper;
    @Autowired
    private GitlabApiClient client;

//    @Scheduled(cron = "0 08 22 * * ? ")
    public void executeCodeCount(){
        //1、更新并查询所有项目
        executeProject();
        List<GitProject> gitProjects = gitProjectMapper.selectList(Wrappers.<GitProject>lambdaQuery());
        if(gitProjects == null || gitProjects.isEmpty()){
            return;
        }

//        List<String> days = DateUtilEx.getDays("2022-05-01","2022-05-31");

        String startDate = "2025-01-01";
        String endDate = "2025-05-01";

        List<CompletableFuture<Boolean>> cfArr = new ArrayList<>();
        for (GitProject project : gitProjects) {
            cfArr.add(CompletableFuture.supplyAsync(() ->
                    collectCode(project.getProjectId()+"", startDate, endDate)));
        }
        if(cfArr != null || cfArr.size() > 0){
            CompletableFuture.allOf(cfArr.toArray(new CompletableFuture[cfArr.size()])).join();
        }

    }

    /**
     * @Author wanshuang
     * @Description 统计项目提交code
     * @param startDate yyyy-MM-dd
     * @param endDate yyyy-MM-dd
     * @Date 2022/4/29
     * @return void
     **/
    private Boolean collectCode(String projectId, String startDate, String endDate){
        //1、获取项目所有分支
        List<GitlabProjectBranchResp> branchResps = getProjectBranchs(projectId);
        if(branchResps == null || branchResps.isEmpty()){
            return true;
        }

        //2、获取项目所有commit
        List<GitlabProjectBranchCommitResp> branchCommitResps = new ArrayList<>();
        for (GitlabProjectBranchResp branchResp : branchResps) {
            List<GitlabProjectBranchCommitResp> commitResps = getProjectBranchCommits(projectId, branchResp.getName(), startDate, endDate);
            if(commitResps == null || commitResps.isEmpty()){
                continue;
            }
            branchCommitResps.addAll(commitResps);
        }

        if(branchCommitResps == null || branchCommitResps.isEmpty()){
            return true;
        }
        branchCommitResps = branchCommitResps.stream().filter(distinctByKey1(s -> s.getId())).collect(Collectors.toList());

        //3、根据项目、提交人、日期统计代码行数
        Map<String, GitCodeCount> codeCountMap = new HashMap<>();
        for (GitlabProjectBranchCommitResp commitResp : branchCommitResps) {
            if(commitResp.getStats() == null || commitResp.getStats().isEmpty()){
                continue;
            }
            String key = DateUtil.format(commitResp.getCommittedDate(), DatePattern.NORM_DATE_PATTERN)+"$$"+commitResp.getCommitterName();
            GitCodeCount codeCount = codeCountMap.get(key);
            if(codeCount == null){
                codeCount = new GitCodeCount();
                codeCount.setProjectId(projectId);
                codeCount.setAddRowCount(0);
                codeCount.setDeleteRowCount(0);
                codeCount.setTotalRowCount(0);
                codeCount.setCommitterName(commitResp.getCommitterName());
                codeCount.setCommitterEmail(commitResp.getCommitterEmail());
                codeCount.setOperDate(DateUtil.format(commitResp.getCommittedDate(), DatePattern.NORM_DATE_PATTERN));
                codeCount.setCreateTime(new Date());
            }

            for (GitlabProjectBranchCommitResp.Stat stat : commitResp.getStats()) {
                if(stat.getAdditions() != null){
                    codeCount.setAddRowCount(codeCount.getAddRowCount() + stat.getAdditions());
                    codeCount.setDeleteRowCount(codeCount.getDeleteRowCount() + stat.getDeletions());
                    codeCount.setTotalRowCount(codeCount.getTotalRowCount() + stat.getTotal());
                }

            }
            codeCountMap.put(key, codeCount);
        }

        //4、保存数据库
        for (Map.Entry<String, GitCodeCount> entry : codeCountMap.entrySet()) {
            gitCodeCountMapper.insert(entry.getValue());
        }

        return true;

    }

    static <T> Predicate<T> distinctByKey1(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * @Author wanshuang
     * @Description 获取项目所有分支
     * @Date 2022/4/29
     **/
    private List<GitlabProjectBranchResp> getProjectBranchs(String projectId){
        return client.getList(new GitlabProjectBranchReq(Integer.valueOf(projectId)), GitlabProjectBranchResp.class);
    }

    /**
     * @Author wanshuang
     * @Description 获取项目分支在某时间段的commit
     * @Date 2022/4/29
     **/
    private List<GitlabProjectBranchCommitResp> getProjectBranchCommits(String projectId,
                                                                  String branchName, String startDate, String endDate){
        GitlabProjectBranchCommitReq commitReq = new GitlabProjectBranchCommitReq();
        commitReq.setProjectId(Integer.valueOf(projectId));
        commitReq.setRefName(branchName);
        commitReq.setSince(DateUtilEx.getIso8601TimestampFromDateStr(DateUtilEx.yyyyMMddAddStartTime(startDate)));
        commitReq.setUntil(DateUtilEx.getIso8601TimestampFromDateStr(DateUtilEx.yyyyMMddAddEndTime(endDate)));
        List<GitlabProjectBranchCommitResp> branchCommitResps =  client.getList(commitReq, GitlabProjectBranchCommitResp.class);
        if(branchCommitResps == null || branchCommitResps.isEmpty()){
            return null;
        }

        branchCommitResps.stream().filter(e->{
            if(StringUtils.isNotBlank(e.getTitle())
                    && (e.getTitle().contains("Merge") || e.getTitle().contains("合并"))){
                return false;
            }
            if(StringUtils.isNotBlank(e.getMessage())
                    && (e.getMessage().contains("Merge") || e.getMessage().contains("合并"))){
                return false;
            }
            return true;
        }).collect(Collectors.toList());


        return branchCommitResps;

    }

    /**
     * @Author wanshuang
     * @Description 更新git项目
     * @Date 2022/4/29
     * @return void
     **/
    public void executeProject() {
        long t1 = System.currentTimeMillis();
        try {
            //1、获取所有账号
            List<GitlabProjectResp> projectRespsList = client.getList(new GitlabProjectReq(), GitlabProjectResp.class);
            for (GitlabProjectResp projectResp : projectRespsList) {
                GitProject gitProject = gitProjectMapper.selectOne(Wrappers.<GitProject>lambdaQuery().eq(GitProject::getProjectId,projectResp.getId()+""));

                //1.1、新增或更新标记，0：新增，1，更新
                Boolean isUpdate = true;
                if(gitProject == null){
                    gitProject = new GitProject();
                    gitProject.setProjectId(projectResp.getId()+"");
                    isUpdate = false;
                }

                //1.2、重新赋值
                gitProject.setName(projectResp.getName()+"");
                gitProject.setDescription(projectResp.getDescription()+"");
                gitProject.setDefaultBranch(projectResp.getDefaultBranch()+"");
                gitProject.setTagCount(projectResp.getTagList() == null ? 0 : projectResp.getTagList().size());
                gitProject.setCreateTime(projectResp.getCreatedAt());
                gitProject.setUpdateTime(new Date());

                //1.3、新增或更新数据表
                if(!isUpdate){
                    gitProjectMapper.insert(gitProject);
                }else{
                    gitProjectMapper.update(gitProject, Wrappers.<GitProject>lambdaQuery().eq(GitProject::getId,gitProject.getId()));
                }
            }
        }finally {
            log.info("更新gitlab项目完成，耗时"+(System.currentTimeMillis()-t1)+"ms");
        }

    }


    /**
     * @Author wanshuang
     * @Description 更新git账号
     * @Date 2022/4/29
     * @return void
     **/
//    @Scheduled(cron = "0 0 23  * * ? ")   //每天23点更新
    public void executeUpdateUser() {
        long t1 = System.currentTimeMillis();
        try {
            //1、获取所有账号
            List<GitlabUserResp> userList = client.getList(new GitlabUserReq(), GitlabUserResp.class);
            for (GitlabUserResp userResp : userList) {
                GitUser gitUser = gitUserMapper.selectOne(Wrappers.<GitUser>lambdaQuery().eq(GitUser::getUserId,userResp.getId()));
                //1.1、获取账号详情
                GitlabUserDetailResp userDetailResp = client.getOne(new GitlabUserDetailReq(userResp.getId()),
                        GitlabUserDetailResp.class);

                //1.2、新增或更新标记，0：新增，1，更新
                Boolean isUpdate = true;
                if(gitUser == null){
                    gitUser = new GitUser();
                    gitUser.setUserId(userResp.getId()+"");
                    isUpdate = false;
                }

                //1.3、重新赋值
                gitUser.setState(userResp.getState());
                gitUser.setName(userResp.getName());
                gitUser.setUserName(userResp.getUsername());
                gitUser.setCreateTime(userDetailResp.getCreatedAt());
                gitUser.setUpdateTime(new Date());

                //1.4、新增或更新数据表
                if(!isUpdate){
                    gitUserMapper.insert(gitUser);
                }else{
                    gitUserMapper.update(gitUser, Wrappers.<GitUser>lambdaQuery().eq(GitUser::getId,gitUser.getId()));
                }

            }
        }finally {
            log.info("更新gitlab账号完成，耗时"+(System.currentTimeMillis()-t1)+"ms");
        }

    }

}