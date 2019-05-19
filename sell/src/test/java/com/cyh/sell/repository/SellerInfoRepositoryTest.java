package com.cyh.sell.repository;

import com.cyh.sell.dataobject.SellerInfo;
import com.cyh.sell.utils.KeyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();

        sellerInfo.setSellerId(KeyUtils.gen());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");

        repository.save(sellerInfo);

    }

    @Test
    public void findByOpenid() {
//        SellerInfo byOpenid = repository.findByOpenid("abc");
//        System.out.println(byOpenid);
    }
}