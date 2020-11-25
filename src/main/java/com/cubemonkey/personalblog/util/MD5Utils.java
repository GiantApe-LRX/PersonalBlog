package com.cubemonkey.personalblog.util;

import org.springframework.util.DigestUtils;

/**
 * MD5加密工具类
 * @author CubeMonkey
 * @create 2020-11-23 10:11
 */
public class MD5Utils {
    /**
     * MD5加密处理
     * @param str 待加密的字符串
     * @param salt 盐值
     * @return 加密后的字符串
     */
    public static String encodeMD5(String str, Integer salt){
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] += salt;
        }
        return DigestUtils.md5DigestAsHex(bytes);
    }
}
