package com.cyh.sell.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckActionUtils {

    public static String sha1(String str){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = digest.digest(str.getBytes());
            return toHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    private static String toHex(byte[] bytes) {
        String str = "";
        for (byte b : bytes) {
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            char[] temp = new char[2];
            temp[0] = chars[(b >>> 4) & 0x0F];
            temp[1] = chars[b & 0x0F];

            str += new String(temp);
        }
        return str;
    }
}
