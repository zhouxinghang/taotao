package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户登录注册页面显示Controller
 * Created by admin on 2017/12/30.
 */
@Controller
public class PageController {
    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }
    @RequestMapping("/page/login")
    public String showLogin() {
        return "login";
    }

}
