package com.bst.gitlab.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 研发组织
 * </p>
 *
 * @author system
 * @since 2022-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DevelopOrganize implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     * 序列号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 组织编号
     */
    private Integer code;

    /**
     * 组织名称
     */
    private Integer name;

    /**
     * 钉钉AccessToken
     */
    private String dingAccessToken;

    /**
     * 钉钉secret
     */
    private String dingSecret;


}
