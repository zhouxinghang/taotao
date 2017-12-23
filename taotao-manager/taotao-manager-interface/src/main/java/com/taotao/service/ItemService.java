package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;

/**
 * Created by admin on 2017/12/23.
 */
public interface ItemService {
    TbItem getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page, int rows);
}
