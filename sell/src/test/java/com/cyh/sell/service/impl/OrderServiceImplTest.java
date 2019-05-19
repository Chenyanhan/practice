package com.cyh.sell.service.impl;

import com.cyh.sell.dataobject.OrderDetail;
import com.cyh.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Resource
    private OrderServiceImpl orderService;

    private final String opendId = "100001";
    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("以为买家");
        orderDTO.setBuyerAddress("adress");
        orderDTO.setBuyerPhone("10010");
        orderDTO.setBuyerOpenid(opendId);

        //订单详情
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setProductId("456");
        orderDetail.setProductQuantity(1);

        orderDetails.add(orderDetail);
        orderDTO.setOrderDetails(orderDetails);

        orderService.create(orderDTO);
    }

    @Test
    public void findOne() {
        OrderDTO one = orderService.findOne("1555935826002155485");
        System.out.println(one);
    }

    @Test
    public void findList() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderDTO> list = orderService.findList("110110", pageRequest);
        for (OrderDTO o :
                list) {
            System.out.println(o);
        }
        System.out.println(list);
    }

    @Test
    public void cancelOrder() {
        OrderDTO one = orderService.findOne("112");

        OrderDTO orderDTO = orderService.cancelOrder(one);

        System.out.println(orderDTO);
    }

    @Test
    public void finished() {

        OrderDTO one = orderService.findOne("1555935826002155485");

        OrderDTO orderDTO = orderService.finished(one);

        System.out.println(orderDTO);
    }

    @Test
    public void paid() {
        OrderDTO one = orderService.findOne("1556177355286659807");

        OrderDTO paid = orderService.paid(one);

        System.out.println(paid);
    }
}