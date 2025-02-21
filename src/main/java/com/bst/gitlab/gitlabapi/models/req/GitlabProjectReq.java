package com.bst.gitlab.gitlabapi.models.req;

import com.bst.gitlab.gitlabapi.models.PageBaseReq;
import com.bst.gitlab.gitlabapi.models.ReqConfigBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class GitlabProjectReq extends PageBaseReq implements ReqConfigBase {

    /**
     * 请求路径
     **/
    public static final String URL = "projects";

    /**
     * 请求路径
     **/
    private String simple = Boolean.TRUE.toString();

    /**
     * 排序字段
     **/
    @JsonProperty("order_by")
    private String orderBy="id";
    /**
     * 升序/降序
     **/
    private String sort="asc";

    @Override
    public String URL() {
        return URL;
    }
}
