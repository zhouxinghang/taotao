package com.taotao.order.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.dao.TbOrderDao;
import com.taotao.dao.TbOrderItemDao;
import com.taotao.dao.TbOrderShippingDao;
import com.taotao.jedis.JedisClient;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2018/1/2.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderDao orderDao;
    @Autowired
    private TbOrderItemDao orderItemDao;
    @Autowired
    private TbOrderShippingDao orderShippingDao;
    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_BEGIN_VALUE}")
    private String ORDER_ID_BEGIN_VALUE;
    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        //订单号生成，可以用redis的incr生成（单例）
        if (!jedisClient.exists(ORDER_ID_GEN_KEY)) {
            //若redis不存在此自增序列，就生成，并设置初始值
            jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        //向订单表中插入数据,补全pojo信息
        orderInfo.setOrderId(orderId);
        //TODO 邮费暂时设置为免邮
        orderInfo.setPostFee("0");
        //TODO 暂时只支持货到付款，所以状态为未付款
        orderInfo.setStatus(1);
        //订单创建时间
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //向订单表中插入数据
        orderDao.insert(orderInfo);
        //向订单明细表中插入数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for(TbOrderItem orderItem : orderItems) {
            //获得主键,这个不需要设置初始值，可以直接从零开始
            String orderItemId = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            orderItem.setId(orderItemId);
            orderItem.setOrderId(orderId);
            orderItemDao.insert(orderItem);
        }
        //向订单物流表插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setCreated(new Date());
        orderShipping.setOrderId(orderId);
        orderShipping.setUpdated(new Date());
        orderShippingDao.insert(orderShipping);
        //返回订单号
        return TaotaoResult.ok(orderId);
    }
}
