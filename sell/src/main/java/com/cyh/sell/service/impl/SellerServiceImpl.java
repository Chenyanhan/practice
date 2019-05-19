package com.cyh.sell.service.impl;

import com.cyh.sell.dataobject.SellerInfo;
import com.cyh.sell.repository.SellerInfoRepository;
import com.cyh.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoRepository repository;
    @Override
    public SellerInfo findBySellerOpenId(String openid) {


        if (StringUtils.isEmpty(openid)){
            SellerInfo byOpenid = repository.findByOpenid(openid);
        }
        return null;
    }
}
