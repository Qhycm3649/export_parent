package com.xyou.web.controller.exceptions;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //1 创建视图对象
        ModelAndView mv = new ModelAndView();
        //2 设置信息保存到请求域中
        mv.addObject("errorMsg", e.getMessage());
        //3.设置跳转视图名称
        mv.setViewName("error");
        //4. 返回
        return mv;
    }
}
