package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

/**
 * Created by admin on 2018/1/2.
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderInfo
     * @return
     */
    TaotaoResult createOrder(OrderInfo orderInfo);
}
