
package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.dao.TbItemDao;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemQuery;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemQuery query = new TbItemQuery();
        List<TbItem> tbItems = tbItemDao.selectByExample(query);
        //取查询结果
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(tbItems);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(tbItems);
        result.setTotal(pageInfo.getTotal());
        return result;
    }
}
