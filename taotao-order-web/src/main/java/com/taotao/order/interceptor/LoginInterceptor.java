package com.taotao.order.interceptor;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2018/1/2.
 * 判断用户是否登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @Value("${SSO_URL}")
    private String SSO_URL;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取Token信息
        String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
        //如果娶不到tolen，跳转到登录页面，需要将当前的url做为参数传递给sso，sso登录成功后回调
        if (StringUtils.isEmpty(token)) {
            String requestURL = request.getRequestURL().toString();
            response.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
            return false;
        }
        //取到token后，调用sso系统的服务判断用户是否登录
        TaotaoResult checkResult = userService.getUserByToken(token);
        //如未取到用户信息，跳转到登录页面
        if (checkResult.getStatus() != 200) {
            String requestURL = request.getRequestURL().toString();
            response.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
            return false;
        }
        //如取到用户信息，放行
        TbUser user = (TbUser) checkResult.getData();
        request.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
