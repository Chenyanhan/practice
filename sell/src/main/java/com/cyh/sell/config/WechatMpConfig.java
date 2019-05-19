package com.cyh.sell.config;


import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatMpConfig {
    @Autowired
    private WechatAccountConfig accountConfig;
    @Bean
    public WxMpServiceImpl wxMpService(){
        WxMpServiceImpl service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(wxMpConfigStorage());
        return service;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        ((WxMpInMemoryConfigStorage) wxMpConfigStorage).setAppId(accountConfig.getMpAppId());
        ((WxMpInMemoryConfigStorage) wxMpConfigStorage).setSecret(accountConfig.getMpAppSecret()  );
        return wxMpConfigStorage;
    }
}
