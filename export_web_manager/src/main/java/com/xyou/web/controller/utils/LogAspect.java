package com.xyou.web.controller.utils;

import com.xyou.domain.system.SysLog;
import com.xyou.domain.system.User;
import com.xyou.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;


@Component
@Aspect//该类是一个切面类
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private HttpServletRequest request;

    //切入点表达式是可以使用逻辑运算符
    @Around("execution(* com.xyou.web.controller.*.*.*(..)) &&! execution(* com.xyou.web.controller.*.SysLogController.*(..))")
    public Object saveLog(ProceedingJoinPoint pj) {

        try {
            Object result = pj.proceed();//放行目标方法

            //做日志
            SysLog sysLog = new SysLog();
            //给日志补充数据
            sysLog.setId(UUID.randomUUID().toString());
            //得到session
            User loginUser = (User) request.getSession().getAttribute("loginUser");
            //设置登录者的名字
            sysLog.setUserName(loginUser.getUserName());
            //设置ip
            sysLog.setIp(request.getRemoteAddr());
            //设置时间
            sysLog.setTime(new Date());
            //设置当前执行的方法
            String methodName = pj.getSignature().getName();
            sysLog.setMethod(methodName);

            //设置方法的所属的类名
            //pj.getTarget 获取当前调用方法的目标对象
            String className = pj.getTarget().getClass().getName();
            sysLog.setAction(className);
            sysLog.setCompanyId(loginUser.getCompanyId());
            sysLog.setCompanyName(loginUser.getCompanyName());
            sysLogService.save(sysLog);

            //目标方法的执行结果一定要返回
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
