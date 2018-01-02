package com.taotao.cart.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/1/2.
 * 购物车管理Controller
 */

@Controller
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    @Value("${CART_KEY}")
    private String CART_KEY;
    @Value("${CART_EXPIER}")
    private Integer CART_EXPIER;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/cart/add/{itemId}")
    public String addItemCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response) {
        //取购物车商品列表
        List<TbItem> cartItemList = getCartItemList(request);
        //判断购物车是否存在该商品
        boolean flag = false;
        for(TbItem item : cartItemList) {
            if(item.getId() == itemId.longValue()) {
                item.setNum(item.getNum() + num);
                flag = true;
                break;
            }
        }
        //购物车不存在该商品，就新建一个
        if(!flag) {
            //需要调用服务取商品信息
            TbItem item = itemService.getItemById(itemId);
            //防止空指针
            if(item == null) {
                //重定向到购物车列表页面
                return "redirect:/cart/cart.html";
            }
            item.setNum(num);
            //取一张图片
            String image = item.getImage();
            if(!StringUtils.isEmpty(image)) {
                String[] images = image.split(",");
                item.setImage(images[0]);
            }
            //将商品放入购物车
            cartItemList.add(item);
        }

        CookieUtils.setCookie(request, response,  CART_KEY,  JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
        return "cartSuccess";
    }

    @GetMapping("/cart/cart")
    public String showCartList(HttpServletRequest request) {
        List<TbItem> tbItems = getCartItemList(request);
        //把购物车信息传给jsp
        request.setAttribute("cartList", tbItems);
        return "cart";
    }


    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateItemNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
                                      HttpServletResponse response) {
        //获取购物车信息
        List<TbItem> cartItemList = getCartItemList(request);
        for(TbItem item : cartItemList) {
            if(item.getId() == itemId.longValue()) {
                //item.setNum(item.getNum() + num);
                item.setNum(num);
                break;
            }
        }
        //将购物车信息加入到Cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
        //返回结果
        return TaotaoResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取出购物车信息
        List<TbItem> cartItemList = getCartItemList(request);
        for(TbItem item : cartItemList) {
            if(item.getId() == itemId.longValue()) {
                cartItemList.remove(item);
                break;
            }
        }
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER,true);
        //重定向到购物车列表页面
        return "redirect:/cart/cart.html";
    }



    private List<TbItem> getCartItemList(HttpServletRequest request) {
        //从cookie中取出
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if(StringUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        return JsonUtils.jsonToList(json, TbItem.class);
    }



}
