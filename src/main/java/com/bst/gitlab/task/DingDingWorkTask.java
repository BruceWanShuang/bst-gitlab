package com.bst.gitlab.task;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bst.gitlab.dao.GitCodeCountMapper;
import com.bst.gitlab.dao.model.GitCodeCount;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wanshuang
 * @Email wanshuang@scqcp.com
 * @Date 2022年04月29日 15:52
 * @Vsersion 1.0
 * https://open.dingtalk.com/document/robots/custom-robot-access
 **/
@Component
@Log4j2
public class DingDingWorkTask {

    @Autowired
    GitCodeCountMapper gitCodeCountMapper;

//    @Scheduled(cron = "0/30 * *  * * ? ")
    public void executeUpdateUser() throws Exception {
        String url = "https://oapi.dingtalk.com/robot/send?access_token=a4a490d9d3f84158982e5466f60b33b2f7fd34802207013302a7095bc505c52c&timestamp=%s&sign=%s";
        Long timestamp = System.currentTimeMillis();
        String sign  = sign(timestamp);
        DingTalkClient client = new DefaultDingTalkClient(String.format(url, timestamp, sign));
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("企业端站务线");

        List<GitCodeCount> gitCodeCounts = gitCodeCountMapper.selectList(Wrappers.<GitCodeCount>lambdaQuery().eq(GitCodeCount::getCommitterName,"兰鹏"));
        gitCodeCounts.stream().collect(Collectors.toMap(GitCodeCount::getCommitterName, GitCodeCount -> GitCodeCount));

        List<LinkedHashMap> list = new ArrayList<>();
        for (GitCodeCount codeCount : gitCodeCounts) {
            list.add(JSONUtil.toBean(JSONUtil.toJsonStr(codeCount), LinkedHashMap.class));
        }
        markdown.setText(prt(list));
        request.setMarkdown(markdown);
        OapiRobotSendResponse response = client.execute(request);
        System.out.println(JSONUtil.toJsonStr(response));

    }

    private  String sign(Long timestamp) throws Exception {
        String secret = "SECeee5229e579d041b898760591513cacd85737308b05d602610ef04ae593633ff";
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
    }


    private static String prt(List<LinkedHashMap> list){
        if(list == null || list.size() == 0) {
            return "";
        }

        LinkedHashMap temp = list.get(0);

        String str = "|";
        for(Object obj: temp.keySet()){
            str += obj + "|";
        }
        str += "\r|";
        for(Object obj: temp.keySet()){
            str += "----|";
        }

        for(LinkedHashMap tempMap: list){
            str += "\r|";
            for(Object obj: tempMap.keySet()){
                str += (tempMap.get(obj) == null ? "" : tempMap.get(obj)) + "|";
            }
        }

        return str;
    }

}
