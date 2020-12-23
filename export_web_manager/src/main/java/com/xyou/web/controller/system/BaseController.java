package com.xyou.web.controller.system;

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

    //获取登录者的企业id（目前是模拟）
    public String getLoginCompanyId() {
        return "1";
    }

    //获取登陆者企业名称
    public String getLoginCompanyName(){
        return "传智播客教育股份有限公司";
    }
}
