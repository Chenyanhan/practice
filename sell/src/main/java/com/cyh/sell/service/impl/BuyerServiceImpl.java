package com.cyh.sell.service.impl;

import com.cyh.sell.dto.OrderDTO;
import com.cyh.sell.enums.ResultEnums;
import com.cyh.sell.exception.SellException;
import com.cyh.sell.service.BuyerService;
import com.cyh.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderid) {
        OrderDTO one = orderService.findOne(orderid);
        if (one == null){
            return null;
        }
        //判断是否是自己的订单
        if (!one.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("openid不一致");
            throw new SellException(ResultEnums.ORDER_OWNER_ERRO);
        }
        return one;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderid) {
        OrderDTO one = orderService.findOne(orderid);
        if (one == null){
            log.error("查询不到该订单");
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }
        if (!one.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("openid不一致");
            throw new SellException(ResultEnums.ORDER_OWNER_ERRO);
        }

        return orderService.cancelOrder(one);
    }
}
