package com.cyh.sell.service.impl;

import com.cyh.sell.dataobject.ProductCategory;
import com.cyh.sell.repository.ProductCategoryRepository;
import com.cyh.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository repository;
    @Override
    public ProductCategory findOne(Integer categoryId) {

        Optional<ProductCategory> category = repository.findById(categoryId);

        return category.get();
    }

    @Override
    public List<ProductCategory> findAll() {

        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
