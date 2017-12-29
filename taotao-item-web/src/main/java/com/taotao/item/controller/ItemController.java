package com.taotao.item.controller;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by admin on 2017/12/29.
 * 商品详情页展示
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model) {
        //取商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        //取商品描述
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
        //把数据传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        //返回逻辑视图
        return "item";
    }
}
