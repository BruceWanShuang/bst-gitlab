package com.bst.gitlab.gitlabapi.models.req;

import com.bst.gitlab.gitlabapi.models.ReqConfigBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author wanshuang
 * @Email wanshuang@scqcp.com
 * @Date 2022年04月28日 14:12
 * @Vsersion 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitlabUserDetailReq implements ReqConfigBase {

    /**
     * 请求路径
     **/
    public static final String URL = "users/%s";

    /**
     * 用户编号
     **/
    private Integer id;

    @Override
    public String URL() {
        return String.format(URL, id);
    }
}
