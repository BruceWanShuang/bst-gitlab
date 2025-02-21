package com.bst.gitlab.gitlabapi.models.res;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GitlabUserResp {

    /**
     * 账号编号
     **/
    private Integer id;

    /**
     * 账号中文名称
     **/
    private String name;

    /**
     * 账号拼音名称
     **/
    private String username;

    /**
     * 账户状态
     **/
    private String state;

}
