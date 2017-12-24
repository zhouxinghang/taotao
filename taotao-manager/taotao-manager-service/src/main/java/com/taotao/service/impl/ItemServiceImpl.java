
package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.dao.TbItemDao;
import com.taotao.dao.TbItemDescDao;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemQuery;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品管理service
 * Created by admin on 2017/12/23.
 */

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemDao tbItemDao;
    @Autowired
    private TbItemDescDao tbItemDescDao;
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

    /**
     * 商品存储，分为两个表  商品+描述
     * id通过工具类生成
     * @param tbItem
     * @param desc
     * @return
     */
    @Override
    public TaotaoResult addItem(TbItem tbItem, String desc) {
        long itemId = IDUtils.getItemId();
        tbItem.setId(itemId);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        tbItem.setStatus((byte)1);//商品状态，1-正常，2-下架，3-删除
        tbItemDao.insert(tbItem);

        //保存desc
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDescDao.insert(tbItemDesc);
        return TaotaoResult.ok();
    }
}
