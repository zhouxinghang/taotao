package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 * Created by admin on 2017/12/23.
 */
public interface ItemService {
    TbItem getItemById(long itemId);

    TbItemDesc getItemDescById(long itemId);

    EasyUIDataGridResult getItemList(int page, int rows);

    TaotaoResult addItem(TbItem tbItem, String desc);
}
