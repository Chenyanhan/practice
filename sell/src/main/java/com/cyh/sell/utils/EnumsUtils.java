package com.cyh.sell.utils;

import com.cyh.sell.enums.CodeEnums;

/**
 * Created by Administrator on 2017/11/1 0001.
 */
public class EnumsUtils {
    private EnumsUtils(){};

    public static <T extends CodeEnums>T getEnumsByCode(Integer code,Class<T> tClass){
        for (T enmu:tClass.getEnumConstants()
             ) {
            if (enmu.getCode().equals(code)){
                return enmu;
            }
        }
        return null;
    }

}


























