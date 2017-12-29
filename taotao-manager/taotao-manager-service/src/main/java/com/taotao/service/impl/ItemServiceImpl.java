
package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.dao.TbItemDao;
import com.taotao.dao.TbItemDescDao;
import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemQuery;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * 商品管理service
 * Created by admin on 2017/12/23.
 */

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private TbItemDao tbItemDao;
    @Autowired
    private TbItemDescDao tbItemDescDao;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name = "itemAddtopic")
    private Destination destination;
    @Autowired
    private JedisClient jedisClient;

    @Value("${ITEM_INFO}")
    private String ITEM_INFO;
    @Value("${ITEM_EXPIRE}")
    private Integer ITEM_EXPIRE;

    @Override
    public TbItem getItemById(long itemId) {
        //查询数据前先查询缓存
        try {
            String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":BASE");
            if(StringUtils.isNotBlank(json)) {
                return JsonUtils.jsonToPojo(json, TbItem.class);
            }
        } catch (Exception e) {
            logger.error("ItemServiceImpl.getItemById.ERROR : {}", e);
        }
        //缓存未命中，查询数据库
        TbItem tbItem = tbItemDao.selectByPrimaryKey(itemId);
        try {
            //结果添加到缓存
            jedisClient.set(ITEM_INFO + ":" + itemId + ":BASE", JsonUtils.objectToJson(tbItem));
            //设置过期时间，提高缓存利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId + ":BASE", ITEM_EXPIRE);
        } catch (Exception e) {
            logger.error("ItemServiceImpl.getItemById.ERROR : {}", e);
        }
        return tbItem;
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        //查询数据前，先查询缓存
        try {
            String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":DESC");
            if(StringUtils.isNotBlank(json)) {
                return JsonUtils.jsonToPojo(json, TbItemDesc.class);
            }
        } catch (Exception e){
            logger.error("ItemServiceImpl.getItemDescById.ERROR : {}", e);
        }
        //缓存未命中，查询数据库
        TbItemDesc tbItemDesc = tbItemDescDao.selectByPrimaryKey(itemId);
        try {
            jedisClient.set(ITEM_INFO + ":" + itemId + ":DESC", JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire(ITEM_INFO + ":" + itemId + ":DESC", ITEM_EXPIRE);
        } catch (Exception e) {
            logger.error("ItemServiceImpl.getItemDescById.ERROR : {}", e);
        }
        return tbItemDescDao.selectByPrimaryKey(itemId);
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
        //向Activemq发送商品添加消息
        jmsTemplate.send(destination, session -> {
            //发送商品id
            TextMessage textMessage = session.createTextMessage(itemId + "");
            return textMessage;
        });
        return TaotaoResult.ok();
    }
}
