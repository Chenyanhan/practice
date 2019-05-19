package com.cyh.sell.repository;

import com.cyh.sell.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Resource
    private ProductCategoryRepository repository;

    @Test
    public void Test1(){
        Optional<ProductCategory> byId = repository.findById(1);
        
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("嘉",3);
        repository.save(productCategory);

    }
}