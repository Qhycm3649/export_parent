package com.xyou.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.Dept;
import com.xyou.domain.system.Role;
import com.xyou.domain.system.User;
import com.xyou.service.system.DeptService;
import com.xyou.service.system.RoleService;
import com.xyou.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//用户管理web层
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    //实例业务层
    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    /**
     * 作用：进入用户列表页面
     * 路径：/system/user/list.do
     * 参数：无
     * 返回追：system/user/user-list.jsp
     */
    @RequestMapping("/list")
    @RequiresPermissions("用户管理")
    public String user(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {

       /* //硬编码做权限校验
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("用户管理");*/

        //设置企业id
        String companyId = getLoginCompanyId();
        //调用分页方法
        PageInfo<User> pageInfo = userService.findAll(pageNum, pageSize, companyId);
        System.out.println(pageInfo.getList());
        //保存到请求域中
        request.setAttribute("pageInfo", pageInfo);
        //返回
        return "system/user/user-list";

    }


    /**
     * 作用：进入用户添加页面
     * 路径：/system/user/toAdd.do
     * 参数：无
     * 返回追：system/user/user-add.jsp
     */
    @RequestMapping("/toAdd")
    public String toAdd() {

        //1. 查询所有用户
        String companyId = getLoginCompanyId();
        List<Dept> deptList = deptService.findAllByDeptId(companyId);
        //2. 存储到请求域中
        request.setAttribute("deptList", deptList);
        return "system/user/user-add";
    }


    /**
     * 作用：进入用户修改页面
     * 路径：/system/user/toUpdate.do
     * 参数：用户id
     * 返回追：system/user/user-update.jsp
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        User user = userService.findByUserId(id);
        request.setAttribute("user", user);

        //1. 查询所有用户
        String companyId = getLoginCompanyId();
        List<Dept> deptList = deptService.findAllByDeptId(companyId);
        //2. 存储到请求域中
        request.setAttribute("deptList", deptList);
        return "system/user/user-update";
    }


    /**
     * 作用：进入用户修改或保存
     * 路径：/system/user/edit.do
     * 参数：用户id
     * 返回追：system/user/user-list.jsp
     */
    @RequestMapping("/edit")
    public String edit(User user) {

        //1. 给用户补充企业的id与企业名称
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        if (StringUtils.isEmpty(user.getId())) {
            //调用方法进行保存
            userService.save(user);
            System.out.println(user);
        } else {
            //调用方法进行修改
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }


    /*
         作用：删除用户
         路径： /system/user/delete.do
         参数： 用户的id
         返回值：json对象
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String id) {

        boolean flag = userService.delete(id);
        Map<String, Object> map = new HashMap<>();
        map.put("flag", flag);
        if (flag) {
            map.put("message", "删除成功");
        } else {
            map.put("message", "有孩子不能删除");
        }
        return map;
    }


    /*
          作用：用户分配角色页面
          路径：/system/user/roleList.do?id=002108e2-9a10-4510-9683-8d8fd1d374ef
          参数： 用户的id
          返回值：system/user/user-role
       */
    @RequestMapping("/roleList")
    public String roleList(String id) {

        //1. 查找到当前的用户
        User user = userService.findByUserId(id);
        //2. 查找到所有的角色
        String companyId = getLoginCompanyId();
        List<Role> roleList = roleService.findAllRole(companyId);
        //3. 查找到当前用户已经具备的角色id
        List<String> userRoleIds=roleService.findUserRoleByUserId(id);

        //存储到请求域
        request.setAttribute("user",user);
        request.setAttribute("roleList",roleList);
        request.setAttribute("userRoleStr",userRoleIds.toString());
        return "system/user/user-role";
    }

    /*
     作用：保存修改用户的角色
     路径：/system/user/changeRole.do
     参数： userid : 用户的id  roleIds: 角色的id
     返回值：用户列表页面
     */
    @RequestMapping("/changeRole")
    public String changeRole(String userid,String[] roleIds){
        userService.changeRole(userid,roleIds);
        return "redirect:/system/user/list.do";
    }
}
