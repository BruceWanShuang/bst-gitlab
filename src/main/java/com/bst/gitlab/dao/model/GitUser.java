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
 * git账号
 * </p>
 *
 * @author system
 * @since 2022-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GitUser implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     * 序列号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号编号
     */
    private String userId;

    /**
     * 账号状态
     */
    private String state;

    /**
     * 账号中文名称
     */
    private String name;

    /**
     * 账号拼音名称
     */
    private String userName;

    /**
     * 账号邮箱
     */
    private String email;

    /**
     * 组织编号
     */
    private String organizeCode;

    /**
     * 账号创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
