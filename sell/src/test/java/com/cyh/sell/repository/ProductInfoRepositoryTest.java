package com.cyh.sell.repository;

import com.cyh.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Resource
    private ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo(
                "123",
                "皮蛋粥",
                new BigDecimal(3.2),
                100,
                "很好喝的粥",
                "http://xxx.jpg",
                1,
                0
        );
        ProductInfo save = repository.save(productInfo);
        Assert.assertNotNull(save);

    }
    @Test
    public void findByProductStatus() {
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        System.out.println(byProductStatus.size());
        Assert.assertNotEquals(0,byProductStatus.size());

    }

    @Test
    public void findOne() {
        Optional<ProductInfo> byId = repository.findById("123");
        ProductInfo productInfo = byId.get();
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findAll() {
        List<ProductInfo> all = repository.findAll();
        Assert.assertNotEquals(0,all.size());
    }


}