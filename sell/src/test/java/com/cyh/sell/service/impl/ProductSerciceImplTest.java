package com.cyh.sell.service.impl;

import com.cyh.sell.dataobject.ProductInfo;
import com.cyh.sell.enums.ProductStatusEnums;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSerciceImplTest {

    @Autowired
    private ProductServiceImpl service;

    @Test
    public void findOne() {
        ProductInfo one = service.findOne("1");
        Assert.assertEquals("1",one.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = service.findUpAll();
        Assert.assertNotEquals(0,upAll.size());
    }

    @Test
    public void findAll() {

        Pageable pageRequest = PageRequest.of(0,2);
        Page<ProductInfo> all = service.findAll(pageRequest);
        System.out.println(all.getTotalElements());


    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo(
                "456",
                "白粥",
                new BigDecimal(2.5),
                150,
                "清火白粥",
                "http://xxx.jpg",
                1,
                0
        );
        service.save(productInfo);
    }

    @Test
    public void increaseStock() {
    }

    @Test
    public void decreaseStock() {
    }

    @Test
    public void onSale() {
        ProductInfo productInfo = service.onSale("1");
        Assert.assertEquals(ProductStatusEnums.UP,productInfo.getProductStatusEnums());
    }

    @Test
    public void offSale() {
        ProductInfo productInfo = service.offSale("1");
        Assert.assertEquals(ProductStatusEnums.DOWN,productInfo.getProductStatusEnums());
    }
}