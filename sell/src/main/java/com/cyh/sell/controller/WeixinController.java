package com.cyh.sell.controller;

import com.cyh.sell.dataobject.JsonData;
import com.cyh.sell.utils.CheckActionUtils;
import com.cyh.sell.utils.HttpUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/weixin")
public class WeixinController {
    private final String TOKEN = "cyh";

    @GetMapping("/auth")
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

    @GetMapping("/login_url")
    @ResponseBody
    public JsonData loginUrl() throws UnsupportedEncodingException {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=www.baidu.com#wechat_redirect";

        String encode = URLEncoder.encode("http://cccyh.natapp1.cc/sell/weixin/url_callback","utf-8");
        String appid = "wxd0a300502b32d3bb";

        String format = String.format(url, appid, encode);

        System.out.println(format);
        Map<String, Object> map = HttpUtils.doGet(format);


        return JsonData.buildSuccess(map);
    }
    @GetMapping("/url_callback")
    @ResponseBody
    public JsonData wechatUserCallback(@RequestParam(value = "code", required = true) String code,
                                     String state, HttpServletResponse response) throws UnsupportedEncodingException {

        System.out.println(code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxd0a300502b32d3bb&secret=%s&code=%s&grant_type=authorization_code";

        String format_url = String.format(url, code);

        Map<String, Object> map = HttpUtils.doGet(format_url);

        return JsonData.buildSuccess(map);

    }
}


















