package com.cyh.sell.repository;

import com.cyh.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author C
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {
    SellerInfo findByOpenid(String openid);
}
