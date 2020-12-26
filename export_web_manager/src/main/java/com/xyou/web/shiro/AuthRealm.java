package com.xyou.web.shiro;


import com.xyou.domain.system.Module;
import com.xyou.domain.system.User;
import com.xyou.service.system.ModuleService;
import com.xyou.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;
    /*
    作用：登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1. 先把token 强制转换为UserNamePasswordToken,强制转换目的是为了得到用户名与密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String email = usernamePasswordToken.getUsername();//获得账号也就是email
        String password= String.valueOf(usernamePasswordToken.getPassword());//用户本次输入的密码

        /*2、 扥局邮箱查找一个用户，如果查找的用户为空，直接返回null，如果这里返回的是null，
              那么登录的方法会受到UnKnownAccountException(用户不存在的异常)
        */
        User dbUser = userService.findUserByEmail(email);
        if (dbUser == null) {
            return null;
        }
        //3. 如果根据邮箱查找到的用户不为空，则对比密码，只不过密码对比的过程有shiro自己去完成
        /*
            SimpleAuthenticationInfo(Object principal, Object credentials, String realmName)
                principal: 设置登录成功后返回的对象
                credentials: 该用户在数据库中密码
                realmName: 不需要管理
         */
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(dbUser, dbUser.getPassword(), "");
        return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1 获取到登录用户
        User loginUser = (User) principals.getPrimaryPrincipal();
        //2 查询当前用户具备的权限
        List<Module> moduleList = moduleService.findModuleByUser(loginUser);
        //3 给当前用户分配权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //遍历所有的权限
        for (Module module : moduleList) {
            //添加一个全新的标记牡丹石要求改权限的标记必须唯一
            authorizationInfo.addStringPermission(module.getName());

        }
        return authorizationInfo;
    }
}