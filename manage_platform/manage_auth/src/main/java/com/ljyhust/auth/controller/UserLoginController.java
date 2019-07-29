package com.ljyhust.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/29.
 */
@RequestMapping("sso")
@RestController
public class UserLoginController {

    @RequestMapping("login")
    public Object login() {
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", "0000");
        resMap.put("message", "登录成功");
        return resMap;
    }
}
