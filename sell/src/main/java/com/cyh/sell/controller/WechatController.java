package com.cyh.sell.controller;


import com.cyh.sell.config.ProjectUrlConfig;
import com.cyh.sell.config.WechatOpenConfig;
import com.cyh.sell.dataobject.JsonData;
import com.cyh.sell.enums.ResultEnums;
import com.cyh.sell.exception.SellException;
import com.cyh.sell.utils.CheckActionUtils;
import com.cyh.sell.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;

import me.chanjar.weixin.mp.api.WxMpAiOpenService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.BaseWxMpServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.scope.Scope;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    WxMpServiceImpl service;
    private final String TOKEN = "cyh";

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    @GetMapping("/auth")
    @ResponseBody
    public String auth(@RequestParam String signature,
                       @RequestParam String timestamp,
                       @RequestParam String nonce,
                       @RequestParam String echostr) {
        String[] strings = new String[]{TOKEN, timestamp, nonce};

        StringBuilder builder = new StringBuilder();
        Arrays.sort(strings);

        for (int i = 0; i < strings.length; i++) {
            builder.append(strings[i]);
        }
        String res = CheckActionUtils.sha1(builder.toString());

        if (signature.equalsIgnoreCase(res)) {
            System.out.println("成功");
            return echostr;
        }
        System.out.println("失败");
        return "";
    }
    @GetMapping("/authorize")
    public String authorize(@RequestParam(required = false) String returnUrl) throws UnsupportedEncodingException {
        String url = "http://cccyh.natapp1.cc/sell/wechat/userInfo";


        System.out.println(returnUrl);
        String callback = service.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, returnUrl);

        return "redirect:"+callback;
    }

    @GetMapping("/userInfo")
    public String urlCallback(@RequestParam(value = "code") String code,
                              @RequestParam String state) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = service.oauth2getAccessToken(code);

        String openId = wxMpOAuth2AccessToken.getOpenId();
        String accessToken = wxMpOAuth2AccessToken.getAccessToken();

        Map<String,Object> map = new HashMap<>();

        map.put("openId",openId);
        map.put("accessToken",accessToken);

        return "redirect:" + state + "?openid=" + openId;

    }

    //发送请求认证获取code
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam String returnUrl) throws UnsupportedEncodingException {
        String url = projectUrlConfig.getWechatOpenAuthorize() + "/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl,"utf-8"));
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new SellException(ResultEnums.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        log.info("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" + openId;
    }

}
