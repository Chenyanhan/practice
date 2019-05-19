package com.cyh.sell.service;

import com.cyh.sell.dataobject.SellerInfo;

public interface SellerService {
    /**
     * 通过openid查询卖家信息
     * @param openid
     * @return
     */
    SellerInfo findBySellerOpenId(String openid);
}
