package com.xyou.web.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

//Md5加盐加密
public class Md5Demo {

    public static void main(String[] args) {
        String slat = "user1@itcast.cn";
        String password = "123";
        Md5Hash md5Hash = new Md5Hash(password, slat);
        System.out.println("user1加盐加密后的字符串：="+md5Hash.toString());
    }
}
