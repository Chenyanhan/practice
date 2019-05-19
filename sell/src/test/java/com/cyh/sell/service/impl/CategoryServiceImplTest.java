package com.cyh.sell.service.impl;

import com.cyh.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory one = categoryService.findOne(1);
        System.out.println(one);
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = categoryService.findAll();
        System.out.println(all);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> categoryTypeIn = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotEquals(0,categoryTypeIn.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("云成烟雨",5);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);

    }
}