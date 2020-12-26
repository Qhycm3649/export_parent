package com.xyou.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.Dept;
import com.xyou.domain.system.Module;
import com.xyou.service.system.DeptService;
import com.xyou.service.system.ModuleService;
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
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    //实例业务层
    @Autowired
    private ModuleService moduleService;

    @Autowired
    private DeptService deptService;
    /**
     * 作用：进入用户列表页面
     * 路径：/system/module/list.do
     * 参数：无
     * 返回追：system/module/module-list.jsp
     */
    @RequestMapping("/list")
    public String module(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {


        //调用分页方法
        PageInfo<Module> pageInfo = moduleService.findAll(pageNum, pageSize);
        System.out.println(pageInfo.getList());
        //保存到请求域中
        request.setAttribute("pageInfo", pageInfo);
        //返回
        return "system/module/module-list";

    }


    /**
     * 作用：进入用户添加页面
     * 路径：/system/module/toAdd.do
     * 参数：无
     * 返回追：system/module/module-add.jsp
     */
    @RequestMapping("/toAdd")
    public String toAdd() {

        //1. 查询所有用户
        List<Module> deptList = moduleService.findAllModules();
        //2. 存储到请求域中
        request.setAttribute("menus",deptList);
        return "system/module/module-add";
    }


    /**
     * 作用：进入用户修改页面
     * 路径：/system/module/toUpdate.do
     * 参数：用户id
     * 返回追：system/module/module-update.jsp
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        Module module=moduleService.findByModuleId(id);
        request.setAttribute("module",module);

        //1. 查询所有用户
        List<Module> deptList = moduleService.findAllModules();
        //2. 存储到请求域中
        request.setAttribute("menus",deptList);
        return "system/module/module-update";
    }


    /**
     * 作用：进入用户修改或保存
     * 路径：/system/module/edit.do
     * 参数：用户id
     * 返回追：system/module/module-list.jsp
     */
    @RequestMapping("/edit")
    public String edit(Module module) {

        if (StringUtils.isEmpty(module.getId())) {
            //调用方法进行保存
            moduleService.save(module);
        } else {
            //调用方法进行修改
            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";
    }


    /*
         作用：删除用户
         路径： /system/module/delete.do
         参数： 用户的id
         返回值：json对象
     */
    @RequestMapping("/delete")
    public String delete(String id) {

        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }
}
