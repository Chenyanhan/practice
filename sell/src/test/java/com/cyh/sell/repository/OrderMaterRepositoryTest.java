package com.cyh.sell.repository;

import com.cyh.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMaterRepositoryTest {

    @Resource
    private OrderMaterRepository repository;
    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<OrderMaster> byBuyerOpenid = repository.findByBuyerOpenid("110110", pageRequest);
          System.out.println(byBuyerOpenid.getTotalElements());
        Assert.assertNotEquals(0,byBuyerOpenid.getTotalElements());
    }


    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("113");
        orderMaster.setBuyerName("一位爱喝粥的人");
        orderMaster.setBuyerPhone("10086");
        orderMaster.setBuyerAddress("终极肥宅");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setOrderAmount(new BigDecimal(233));
        repository.save(orderMaster);
    }




}