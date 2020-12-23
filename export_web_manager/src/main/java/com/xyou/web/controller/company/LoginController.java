package com.xyou.web.controller.company;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录方法
 */
@Controller
public class LoginController {


    @RequestMapping("/login")
    public String login() {
        //跳转到后台首页/home/main.jsp
        return "home/main";
    }

    @RequestMapping("/home")
    public String home() {
        //作用：加载home/home页面进内容区域
        return "home/home";
    }

}
