package com.bst.gitlab.gitlabapi.models.req;

import com.bst.gitlab.gitlabapi.models.ReqConfigBase;
import lombok.Data;

@Data
public class GitlabProjectBranchCommitCodeReq implements ReqConfigBase {

    /**
     * 请求路径
     **/
    public static final String URL = "projects/%s/repository/commits/%s";

    /**
     * 处理Id
     **/
    private Integer projectId;

    /**
     * 处理Id
     **/
    private String commitId;

    @Override
    public String URL() {
        return String.format(URL, projectId, commitId);
    }
}
