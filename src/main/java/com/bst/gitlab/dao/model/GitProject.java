package com.bst.gitlab.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * git项目
 * </p>
 *
 * @author system
 * @since 2022-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GitProject implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     * 序列号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目默认分支
     */
    private String defaultBranch;

    /**
     * 项目tag数量
     */
    private Integer tagCount;

    /**
     * 项目创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
