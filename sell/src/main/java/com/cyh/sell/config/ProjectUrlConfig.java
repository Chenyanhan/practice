package com.cyh.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "projecturl")
public class ProjectUrlConfig {

    /**微信认证url.*/
    private String wechatMpAuthorize;

    /**微信网页授权登录url.*/
    private String wechatOpenAuthorize;

    /**点餐系统.*/
    private String sell;

}
