package com.ljyhust.demo.web;

import com.alibaba.fastjson.JSONObject;
import com.ljyhust.demo.utils.RestClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;

/**
 * 线程demo
 */
@RestController
@RequestMapping("threadTest")
@Slf4j
public class ThreadTestDemoController {

    @RequestMapping("loop")
    public void threadLoopDemo() throws Exception{
        int num = 0;
        long start = System.currentTimeMillis() / 1000;
        while (true) {
            log.info("====>  测试 Loop");
            num++;
            if (num == Integer.MAX_VALUE) {
                log.info("====> rest num");
                num = 0;
            }

            if (System.currentTimeMillis() / 1000 - start > 1000) {
                return;
            }
        }
    }

    @RequestMapping("blockIo")
    public Object blockIoDemo() throws Exception {
        JSONObject resJson = new JSONObject();
        try {
            String resStr = RestClientUtil.getRestTemplate().getForObject("https://www.google.com.hk", String.class);
            log.info("=====> 获取text/html  {}", resStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resJson.put("code", "100");
        return resJson;
    }

    @RequestMapping("getTime")
    public Object getServerTime() throws Exception {
        log.info("=====> 请求开始");
        JSONObject resJson = new JSONObject();
        String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        resJson.put("code", "100");
        resJson.put("reqTime", format);
        log.info("=====> 请求结束");
        return resJson;
    }
}
