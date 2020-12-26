package com.xyou.web.controller.system;

import com.xyou.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//抽取其他Controller的公共部分
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    //获取当前登陆者
    public User getLoginUser(){
        User loginUser = (User) session.getAttribute("loginUser");
        return loginUser;
    }


    //获取登录者的企业id
    public String getLoginCompanyId(){
        User loginUser = getLoginUser();
        return loginUser.getCompanyId();
    }

    //获取登陆者企业名称
    public String getLoginCompanyName(){
        User loginUser = getLoginUser();
        return loginUser.getCompanyName();
    }

}
