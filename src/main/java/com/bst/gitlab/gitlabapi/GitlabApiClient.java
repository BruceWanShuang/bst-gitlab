package com.bst.gitlab.gitlabapi;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.bst.gitlab.common.utils.DateUtilEx;
import com.bst.gitlab.common.utils.JsonMapperHelper;
import com.bst.gitlab.gitlabapi.bean.GitlabConfig;
import com.bst.gitlab.gitlabapi.models.ReqConfigBase;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author wanshuang
 * @Email wanshuang@scqcp.com
 * @Date 2022年04月27日 13:39
 * @Vsersion 1.0
 * https://docs.gitlab.com/ee/api/users.html
 * https://fairysen.com/542.html
 **/
@Component
@Log4j2
public class GitlabApiClient {

    @Autowired
    private GitlabConfig gitlabConfig;

    /**
     * @Author wanshuang
     * @Description 请求查询多个对象
     * @Date 2022/4/29
     * @return T
     **/
    public <T> List<T>  getList(ReqConfigBase<T> req, Class<T> elementType){
        Map<String, Object> reqMaps = encapReqJsonMap(req);
        List<T> result = Collections.synchronizedList(new ArrayList<>());

        Object page = reqMaps.get("page");
        //1、分支获取数据
        if(!Objects.isNull(page)){
            while (true){
                String tempResult = HttpUtil.get(urlAppendMethod(getGitlabUrl(), req.URL()), reqMaps);
                if(StringUtils.isBlank(tempResult)){
                    break;
                }
                List<T> tempResultList=null;
                try{
                   tempResultList = JSONUtil.toList(tempResult, elementType);
                }catch (Exception e){
                   log.error("url="+urlAppendMethod(getGitlabUrl(), req.URL())+", params="+reqMaps, e);
                }

                if(tempResultList == null || tempResultList.isEmpty()){
                    break;
                }
                if(tempResultList != null && tempResultList.size() > 0){
                    result.addAll(tempResultList);
                    reqMaps.put("page", Integer.valueOf(reqMaps.get("page")+"")+1);
                }

            }
        }else{
            String resultStr = HttpUtil.get(urlAppendMethod(getGitlabUrl(), req.URL()), reqMaps);
            result=JSONUtil.toList(resultStr, elementType);

        }

        return result;
    }

    private String getGitlabUrl(){
        return gitlabConfig.getGitLabUrl();
    }

    /**
     * @Author wanshuang
     * @Description 请求查询单个对象
     * @Date 2022/4/29
     * @return T
     **/
    public <T> T getOne(ReqConfigBase<T> req, Class<T> elementType){
        String resultStr = HttpUtil.get(urlAppendMethod(getGitlabUrl(), req.URL()), encapReqJsonMap(req));
        return JSONUtil.toBean(resultStr, elementType);
    }

    /**
     * 封装请求入参
     **/
    private Map<String, Object> encapReqJsonMap(Object req){
        Map<String, Object> reqMaps = JsonMapperHelper.convertToObject(req, Map.class);
        reqMaps.put("private_token", gitlabConfig.getApiToken());
        return reqMaps;
    }

    /**
     * 处理请求路径
     **/
    public static String urlAppendMethod(String url , String method) {
        StringBuilder newurl = new StringBuilder(url);
        if (!"/".equals(newurl.substring(newurl.length() - 1, newurl.length()))) {
            newurl.append("/");
        }
        newurl.append(method);
        return newurl.toString();
    }

    public static void main(String[] args){
        List<String> ss = DateUtilEx.getDays("2022-05-01","2022-05-01");
        System.out.println(ss);
    }

}
