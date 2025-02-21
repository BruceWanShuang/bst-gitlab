package com.bst.gitlab.gitlabapi.models.req;

import com.bst.gitlab.gitlabapi.models.PageBaseReq;
import com.bst.gitlab.gitlabapi.models.ReqConfigBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitlabProjectBranchReq extends PageBaseReq implements ReqConfigBase {

    /**
     * 请求路径
     **/
    public static final String URL = "projects/%s/repository/branches";

    /**
     * 项目id
     **/
    @JsonProperty("id")
    private Integer projectId;

    @Override
    public String URL() {
        return String.format(URL, projectId);
    }
}
