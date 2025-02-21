package com.bst.gitlab.gitlabapi.models.res;

import lombok.Data;

import java.util.Date;

@Data
public class GitlabUserDetailResp extends GitlabUserResp {

    /**
     * 账户创建时间
     **/
    private Date createdAt;

}
