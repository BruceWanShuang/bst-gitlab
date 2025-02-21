package com.bst.gitlab.gitlabapi.models.req;

import com.bst.gitlab.gitlabapi.models.PageBaseReq;
import com.bst.gitlab.gitlabapi.models.ReqConfigBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitlabProjectBranchCommitReq extends PageBaseReq implements ReqConfigBase {

    /**
     * 请求路径
     **/
    public static final String URL = "projects/%s/repository/commits";

    /**
     * 请求路径
     **/
    @JsonProperty("id")
    private Integer projectId;

    /**
     * 分支名
     **/
    @JsonProperty("ref_name")
    private String refName;
    /**
     * 分支名
     **/
    @JsonProperty("with_stats")
    private Boolean withStats = true;
    /**
     * ？
     **/
    private Boolean all = true;
    /**
     * 开始时间
     **/
    private String since;
    /**
     * 结束时间
     **/
    private String until;

    @Override
    public String URL() {
        return String.format(URL, projectId);
    }
}
