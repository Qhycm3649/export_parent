package com.xyou.web.controller.company;

import com.xyou.domain.system.Module;
import com.xyou.domain.system.User;
import com.xyou.service.system.ModuleService;
import com.xyou.service.system.UserService;
import com.xyou.web.controller.system.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 登录方法
 */
@Controller
public class LoginController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/login")
    public String login(String email, String password) {
        //1. 判断邮箱与密码是否为空，如果为空则跳回到登录页面
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            request.setAttribute("error", "用户名或者密码不能为空");
            //如果返回值不需要经过视图解析器，需要使用redirect或者forward开头
            //return "forword:/login.jsp";
            return "redirect:/login.jsp";
        }
        try {
            //得到subject对象
            Subject subject = SecurityUtils.getSubject();
            //把邮箱和密码封装到一个Token中
            UsernamePasswordToken token = new UsernamePasswordToken(email, password);
            //subject发出认证请求
            subject.login(token);
            //登录成功后,返回一个登录成功的对象,shiro在你登录成功后会往session中做登录成功标记
            User loginUser = (User) subject.getPrincipal();//取登录成功的对象
            //这个session的数据只是为了让我们方便使用登录者的信息,并不是登录成功的标记
            session.setAttribute("loginUser", loginUser);
            //一旦用户登录成功之后,马上就应该查找改用户对应的权限
            List<Module> moduleList = moduleService.findModuleByUser(loginUser);
            session.setAttribute("modules", moduleList);
            return "home/main";
        } catch (IncorrectCredentialsException e) {
            //如果密码或者用户名不正确则抛出IncorrectCredentialsException
            request.setAttribute("error","用户名不存在或者密码错误!");
            return "forward:/login.jsp";
        }catch(UnknownAccountException e){
            //如果用户名不存在则抛出UnknownAccountException
            request.setAttribute("error","用户名不存在或者密码错误!");
            return "forward:/login.jsp";
        }


        /*//2. 根据邮箱查找一个用户，如果没有找到，则提示用户邮箱名不存在，如果存在，则对比密码
        User dbUser = userService.findUserByEmail(email);
        if (dbUser!=null) {
            //用户名是存在的，我们则可以对比密码
            if (dbUser.getPassword().equals(password)) {
                //用户名与密码都正确了
                //登陆成功需要做一个登陆成功标记
                session.setAttribute("loginUser", dbUser);
                //一旦该用户登陆成功之后，马上就应该查找该用户对应的权限
                List<Module> moduleList=moduleService.findModuleByUser(dbUser);
                session.setAttribute("modules",moduleList);
                //登录成功到达主页面
                return "home/main";
            } else {
                // 密码错误的情况
                request.setAttribute("error", "账号或者密码错误");
                return "redirect:login.jsp";
            }
        }else{
            // 用户不存在的情况
            request.setAttribute("error","用户名或者密码错误!");
            //如果返回值不需要经过视图解析器，需要使用redirect或者forward开头
            return "forward:/login.jsp";
        }*/
    }

    //logout.do
    @RequestMapping("/logout")
    public String logout() {
        //1 销毁shiro登录标记
        Subject subject = SecurityUtils.getSubject();
        subject.logout();//这个方法的作用是销毁shiro登录成功标记

        /*session.invalidate();*/
        return "redirect:/login.jsp";
    }


    @RequestMapping("/home")
    public String home() {
        //作用：加载home/home页面进内容区域
        return "home/home";
    }

}
