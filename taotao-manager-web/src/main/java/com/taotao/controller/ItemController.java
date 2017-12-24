package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by admin on 2017/12/23.
 */

@Controller
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private ItemService itemService;

    @GetMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        logger.info("ItemController.getItemById.info: {}", tbItem.getId());
        return tbItem;
    }

    @GetMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @PostMapping("/item/save")
    @ResponseBody
    public TaotaoResult addItem(TbItem tbItem, String desc) {
        TaotaoResult result = itemService.addItem(tbItem, desc);
        return result;
    }
}


