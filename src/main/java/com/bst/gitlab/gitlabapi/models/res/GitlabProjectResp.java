package com.bst.gitlab.gitlabapi.models.res;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GitlabProjectResp {

    private Integer id;

    private String description;

    private String name;

    private String nameWithNamespace;

    private String path;

    private Date createdAt;

    private String defaultBranch;

    private String webUrl;

    private Integer starCount;

    private Integer forksCount;

    private List<String> tagList;

}
