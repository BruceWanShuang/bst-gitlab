package com.bst.gitlab.dao.model;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * git代码统计
 * </p>
 *
 * @author system
 * @since 2022-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GitCodeCount implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     * 序列号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 提交者名称
     */
    private String committerName;

    /**
     * 提交者邮箱
     */
    private String committerEmail;

    /**
     * 新增总行数
     */
    private Integer addRowCount;

    /**
     * 删除总行数
     */
    private Integer deleteRowCount;

    /**
     * 总行数
     */
    private Integer totalRowCount;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 统计日期
     */
    private String operDate;

    /**
     * 创建时间
     */
    private Date createTime;


}
