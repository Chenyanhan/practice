package com.cyh.sell.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Administrator on 2017/10/31 0031.
 */
public class JsonUtils {

    /**
     * 转成json格式输出
     * @param object
     * @return
     */
    public static String getJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);
        return json;
    }
}
