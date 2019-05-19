package com.cyh.sell.service;

import com.cyh.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderDTO);
    /**
     * 查询单个订单
     */
    OrderDTO findOne(String orderId);
    /**
     * 查询多个订单
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
    /**
     *取消订单
     */
    OrderDTO cancelOrder(OrderDTO orderDTO);
    /**
     * 完结订单
     */
    OrderDTO finished(OrderDTO orderDTO);

    /**
     * 订单支付
     */
    OrderDTO paid(OrderDTO orderDTO);

    /**
     * admin查询多个订单
     */
    Page<OrderDTO> findList(Pageable pageable);

}
