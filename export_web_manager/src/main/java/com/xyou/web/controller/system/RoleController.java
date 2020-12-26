package com.xyou.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.Module;
import com.xyou.domain.system.Role;
import com.xyou.service.system.ModuleService;
import com.xyou.service.system.RoleService;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//角色管理
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    //实例业务对象
    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;

    /**
     * 作用：进入角色列表页面
     * 路径：/system/role/list.do
     * 参数：无
     * 返回追：system/role/role-list.jsp
     */
    @RequestMapping("/list")
    public String findAll(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {

        String companyId = getLoginCompanyId();
        //调用方法
        PageInfo<Role> pageInfo = roleService.findAll(pageNum, pageSize, companyId);
        //将查询结果，存放到请求域中
        request.setAttribute("pageInfo", pageInfo);
        //返回
        return "system/role/role-list";
    }


    /**
     * 作用：进入角色新增页面
     * 路径："/system/role/toAdd.do"'
     * 参数：无
     * 返回追："system/role/role-add"
     */
    @RequestMapping("/toAdd")
    public String toAdd() {

        /*String companyId = getLoginCompanyId();
        List<Role> roleList = roleService.findAllRole(companyId);
        request.setAttribute("roleList",roleList);*/
        return "system/role/role-add";
    }


    /**
     * 作用：进入角色修改页面
     * 路径：/system/role/toUpdate.do
     * 参数：角色id
     * 返回追："system/role/role-add"
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        Role role = roleService.findRoleId(id);
        request.setAttribute("role", role);

        /*String companyId = getLoginCompanyId();
        List<Role> roleList = roleService.findAllRole(companyId);
        request.setAttribute("roleList",roleList);*/
        return "system/role/role-update";
    }


    /**
     * 作用：进行保存或修改
     * 路径："/system/role/edit.do"'
     * 参数：role
     * 返回追：system/role/role-add.jsp
     */
    @RequestMapping("/edit")
    public String edit(Role role) {

        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        //判断是否带有id
        if (StringUtils.isEmpty(role.getId())) {
            roleService.save(role);
        } else {
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";

    }


    /**
     * 作用：进行修改页面
     * 路径："/system/role/delete.do"'
     * 参数：角色id
     * 返回追："redirect:/system/role/list.do"
     */
    @RequestMapping("/delete")
    public String delete(String id) {

        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }


    /**
     * 作用：进入角色权限页面
     * 路径："/system/role/roleModule.do"
     * 参数：roleid="+id
     * 返回值："system/role/role-module.jsp"
     */
    @RequestMapping("/roleModule")
    public String roleModule(String roleid) {

        Role role = roleService.findRoleId(roleid);
        request.setAttribute("role", role);
        return "system/role/role-module";
    }


    /*作用：获取ztree所要的数据
    路径： /system/role/getTreeNodes.do?roleid=4028a1c34ec2e5c8014ec2ebf8430001
    参数： roleid ： 角色的id
    返回值：json对象*/
    @RequestMapping("/getTreeNodes")
    @ResponseBody
    public List<Map<String, Object>> getTreeNodes(String roleid) {

        //查出所有的权限
        List<Module> moduleList = moduleService.findAllModules();
        //根据角色id查询当前角色所有的权限
        List<String> modulesIds = moduleService.findRoleModuleByRoleId(roleid);
        //定义一个结果集，返回结果
        List<Map<String, Object>> resultList = new ArrayList<>();
        //4. 遍历所有的权限，每一个module就是对应一个Map
        for (Module module : moduleList) {
            //每一个权限对应一个map
            Map<String, Object> map = new HashMap<>();
            map.put("id", module.getId());
            map.put("name", module.getName());
            map.put("pId", module.getParentId());
            map.put("open", true);
            //判断当前遍历的module的id是否包含在moduleIds中。
            if (modulesIds.contains(module.getId())) {
                map.put("checked", true);
            }
            //把map添加到结果集合中
            resultList.add(map);
        }
        return resultList;
    }

     /*
       作用：保存修改角色权限
       路径：/system/role/updateRoleModule.
       参数： roleid ：角色id ，  moduleIds :本次保存的角色对应的权限id  1,2,3,4
       返回值：角色列表
   */
    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(String roleid,String[] moduleIds){
        roleService.updateRoleModule(roleid,moduleIds);
        return "redirect:/system/role/list.do";
    }
}
