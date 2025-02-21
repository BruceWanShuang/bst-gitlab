package com.bst.gitlab.gitlabapi.bean;

import lombok.Data;

/**
 * @Author wanshuang
 * @Email wanshuang@scqcp.com
 * @Date 2022年04月28日 11:05
 * @Vsersion 1.0
 **/
@Data
public class CodeCount {
    private String name;
    private Integer addRowCount=0;
    private Integer deleteRowCount=0;
    private Integer totalRowCount=0;
}
