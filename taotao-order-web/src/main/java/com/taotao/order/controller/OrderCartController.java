package com.taotao.order.controller;

import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/1/2.
 * 订单确认页面处理Controller
 */

@Controller
public class OrderCartController {
    @Value("${CART_KEY}")
    private String CART_KEY;

    @GetMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        //取用户信息，拦截器已将用户信息存入request
        TbUser user = (TbUser) request.getAttribute("user");
        //根据用户信息取收获地址列表，使用静态数据
        List<TbItem> items = getCartItemList(request);
        request.setAttribute("cartList", items);
        return "/order-cart";
    }


    private List<TbItem> getCartItemList(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }
}
