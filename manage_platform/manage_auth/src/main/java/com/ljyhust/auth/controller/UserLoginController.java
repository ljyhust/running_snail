package com.ljyhust.auth.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/29.
 */
//@RequestMapping("sso")
@Controller
public class UserLoginController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    /*@RequestMapping("login")
    @ResponseBody
    public Object login() {
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", "0000");
        resMap.put("message", "登录成功");
        return resMap;
    }*/

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginHtml() {
        logger.info("====> login <<<<");
        return "index.html";
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal principal) {
        System.out.println(principal);
        return principal;
    }
}
