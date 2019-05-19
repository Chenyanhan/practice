package com.cyh.sell.service;

import com.cyh.sell.dataobject.ProductInfo;
import com.cyh.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架的商品
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //增加库存
    void increaseStock(List<CartDTO> productlist);

    //减少库存
    void decreaseStock(List<CartDTO> productlist);

    //上架
    ProductInfo onSale(String openid);

    //下架
    ProductInfo offSale(String openid);
}

