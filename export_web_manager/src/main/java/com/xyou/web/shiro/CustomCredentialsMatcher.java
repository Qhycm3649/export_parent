package com.xyou.web.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    /*
        把密码对比规则定义在该方法中即可
    */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        //1 把tolen转换成UserNameAndPasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //取出邮箱与密码
        String email = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());//这里是用户输入的院士密码：123

        //3 对用户输入的密码进行加盐加密
        Md5Hash md5Hash = new Md5Hash(password, email);
        String md5Password = md5Hash.toString();//加密后的字符串
        //4 获取该用户在数据库中的密码
        String dbPassword = (String) info.getCredentials();
        //5 对比用户输入的密码与用户在数据库中的密码,并返回
        return dbPassword.equals(md5Password);
    }
}
