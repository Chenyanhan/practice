package com.cyh.sell.utils;

import java.util.Random;

public class RandomIdUtil {
    public static synchronized String getUniqueKey(){
        /**
         * 生成唯一主键
         */
        Random random = new Random();

        Integer i = random.nextInt(900000)+100000;

        return System.currentTimeMillis()+String.valueOf(i);

    }
}
