package com.bst.gitlab.gitlabapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author wanshuang
 * @Email wanshuang@scqcp.com
 * @Date 2022年04月27日 15:47
 * @Vsersion 1.0
 **/
@Data
public class PageBaseReq {

    /**
     * 页码
     **/
    private Integer page=1;
    /**
     * 页容量
     **/
    @JsonProperty("per_page")
    private Integer perPage=100;
}
