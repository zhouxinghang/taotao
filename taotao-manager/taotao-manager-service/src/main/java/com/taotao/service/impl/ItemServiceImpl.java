package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.dao.TbItemDao;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品管理service
 * Created by admin on 2017/12/23.
 */

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemDao tbItemDao;

    @Override
    public TbItem getItemById(long itemId) {
        return tbItemDao.selectByPrimaryKey(itemId);
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        return null;
    }
}
