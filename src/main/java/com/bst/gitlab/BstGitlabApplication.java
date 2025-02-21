package com.bst.gitlab;

import com.bst.gitlab.task.GitLabWorkTask;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@MapperScan("com.bst.gitlab.dao")
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.bst.gitlab")
@RestController
public class BstGitlabApplication {

    @Autowired
    GitLabWorkTask gitLabWorkTask;

    public static void main(String[] args) {
        SpringApplication.run(BstGitlabApplication.class, args);
    }

    @RequestMapping(value = "/test", method= {RequestMethod.GET, RequestMethod.POST})
    public String hello() {
        gitLabWorkTask.executeCodeCount();
        return "Hello World!";
    }

}
