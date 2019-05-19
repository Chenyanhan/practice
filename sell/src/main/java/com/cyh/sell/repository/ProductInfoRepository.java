package com.cyh.sell.repository;

import com.cyh.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author C
 */
@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    List<ProductInfo> findByProductStatus(Integer status);
}
