package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by admin on 2017/12/30.
 * 用户处理Controller
 */

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkUserData(@PathVariable String param, @PathVariable Integer type) {
        TaotaoResult result = userService.checkData(param, type);
        return result;
    }

    @PostMapping("/user/register")
    @ResponseBody
    public TaotaoResult regitster(TbUser user) {
        TaotaoResult result = userService.register(user);
        return result;
    }

}
