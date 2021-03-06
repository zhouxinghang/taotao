package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by admin on 2017/12/30.
 * 用户处理Controller
 */

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

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


    @PostMapping("/user/login")
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletResponse response, HttpServletRequest request) {
        TaotaoResult result = userService.login(username, password);
        //登录成功后，将token写入cookie
        if(result.getStatus() == 200) {
            CookieUtils.setCookie(request, response, TOKEN_KEY, result.getData().toString());
        }
        return result;
    }

    @GetMapping("/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        TaotaoResult result = userService.getUserByToken(token);
        //判断是不是jsonp请求
        if(!StringUtils.isEmpty(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            //设置回调方法
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}
