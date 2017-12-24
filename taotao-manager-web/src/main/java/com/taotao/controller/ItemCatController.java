package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by admin on 2017/12/24.
 */

@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @PostMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(
            @RequestParam(name = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> treeNodes = itemCatService.getItemCatList(parentId);
        return treeNodes;
    }
}
