package com.bst.gitlab.gitlabapi.models.req;

import com.bst.gitlab.gitlabapi.models.PageBaseReq;
import com.bst.gitlab.gitlabapi.models.ReqConfigBase;

/**
 * @Author wanshuang
 * @Email wanshuang@scqcp.com
 * @Date 2022年04月28日 14:12
 * @Vsersion 1.0
 **/
public class GitlabUserReq extends PageBaseReq implements ReqConfigBase {

    /**
     * 请求路径
     **/
    public static final String URL = "users";

    @Override
    public String URL() {
        return URL;
    }
}
