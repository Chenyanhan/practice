package com.cyh.sell.service.impl;

import com.cyh.sell.dataobject.ProductInfo;
import com.cyh.sell.dto.CartDTO;
import com.cyh.sell.enums.ProductStatusEnums;
import com.cyh.sell.enums.ResultEnums;
import com.cyh.sell.exception.SellException;
import com.cyh.sell.repository.ProductInfoRepository;
import com.cyh.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) throws RuntimeException{

        Optional<ProductInfo> byId = repository.findById(productId);

        return byId.get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnums.UP.getCode());

    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> productlist) {
        for (CartDTO cartDTO:productlist
             ) {
            Optional<ProductInfo> byId = repository.findById(cartDTO.getProductId());
            ProductInfo productInfo = byId.get();
            productInfo.setProductStock(productInfo.getProductStock() + cartDTO.getProductQuantity());
            repository.save(productInfo);

        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> productlist) {

        for (CartDTO cartDTO:productlist
             ) {
            Optional<ProductInfo> byId;
            try {
                byId = repository.findById(cartDTO.getProductId());

            }
            catch (Exception e){
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
            }
            ProductInfo productInfo = byId.get();
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();

            if (result<0){
                throw new SellException(ResultEnums.PRODUCT_STOCK_ERRO);
            }

            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo;
        try {
            Optional<ProductInfo> byId = repository.findById(productId);
            productInfo = byId.get();
            //更改状态保存
        }
        catch (Exception e){
            throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnums() == ProductStatusEnums.UP){
            throw new SellException(ResultEnums.PRODUCT_STATUS_ERRO);
        }
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo;
        try {
            Optional<ProductInfo> byId = repository.findById(productId);
            productInfo = byId.get();

        }
        catch (Exception e){
            throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnums() == ProductStatusEnums.DOWN){
            throw new SellException(ResultEnums.PRODUCT_STATUS_ERRO);
        }
        //更改状态保存
        productInfo.setProductStatus(ProductStatusEnums.DOWN.getCode());
        return repository.save(productInfo);
    }
}
