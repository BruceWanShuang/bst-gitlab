package com.bst.gitlab.gitlabapi.models.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitlabProjectBranchResp {

    /**
     * 分支名称
     **/
    private String name;

    /**
     * 是否合并主干
     **/
    private Boolean merged;

}
