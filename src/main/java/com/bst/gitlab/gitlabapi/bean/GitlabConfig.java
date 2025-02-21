package com.bst.gitlab.gitlabapi.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wanshuang
 * @Email wanshuang@scqcp.com
 * @Date 2022年04月29日 10:23
 * @Vsersion 1.0
 **/
@Configuration
@Data
public class GitlabConfig {

    @Value("${gitlab_url}")
    private String gitLabUrl;

    @Value("${gitlab_apitoken}")
    private String apiToken;
}
