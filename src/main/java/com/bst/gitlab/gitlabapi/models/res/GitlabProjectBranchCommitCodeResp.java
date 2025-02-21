package com.bst.gitlab.gitlabapi.models.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GitlabProjectBranchCommitCodeResp {

    /**
     * 分支名称
     **/
    private String projectId;

    /**
     * commitId
     **/
    private String id;

    /**
     * 代码统计集
     **/
    private List<Stat> stats;

    @Data
    private static class Stat{

        /**
         * 新增代码数
         **/
        private Integer additions;
        /**
         * 删除代码数
         **/
        private Integer deletions;
        /**
         * 总计代码数
         **/
        private Integer total;
    }

}
