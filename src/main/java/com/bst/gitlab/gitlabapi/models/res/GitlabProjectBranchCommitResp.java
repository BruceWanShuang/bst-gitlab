package com.bst.gitlab.gitlabapi.models.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitlabProjectBranchCommitResp {

    /**
     * 提交ID
     **/
    private String id;

    /**
     * 提交短ID
     **/
    private String shortId;

    /**
     * 消息
     **/
    private String title;
    /**
     * 提交注释
     **/
    private String message;
    /**
     * 创建时间
     **/
    private Date createdAt;
    /**
     * 提交人
     **/
    private String committerName;
    /**
     * 提交人邮箱
     **/
    private String committerEmail;
    /**
     * 提交日期
     **/
    private Date committedDate;

    /**
     * 代码统计集
     **/
    private List<Stat> stats;

    @Data
    public static class Stat{

        /**
         * 新增代码数
         **/
        private Integer additions;
        /**
         * 删除代码数
         **/
        private Integer deletions;
        /**
         * 总计代码数
         **/
        private Integer total;
    }

}
