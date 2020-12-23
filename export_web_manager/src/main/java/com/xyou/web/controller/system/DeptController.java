package com.xyou.web.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.Dept;
import com.xyou.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作用：进入系统管理页面
 * 路径：/system/dept/list.do
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController{


    //实例业务层
    @Autowired
    private DeptService deptService;

    /**
     * 作用：进入系统管理页面
     * 路径：/system/dept/list.do
     * 参数：没有
     * 返回："system/dept/dept-list"
     */
    //使用分页功能
    @RequestMapping("/list")
    public String findAllByDept(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "5") Integer pageSize, HttpServletRequest request) {

        //自定一个企业id
        String companyId =getLoginCompanyId();
        //调用业务进行查询所有部门
        PageInfo<Dept> pageInfo = deptService.findAllByDept(pageNum, pageSize, companyId);
        //将查询结果放入请求域中
        request.setAttribute("pageInfo", pageInfo);
        //返回："/dept/dept-list.jsp"
        return "system/dept/dept-list";
    }



    /**
     * 作用：进入添加页面
     * 路径：/system/dept/toAdd.do
     * 参数：Dept
     * 返回："system/dept/dept-add"
     */
    @RequestMapping("/toAdd")
    public String toAdd(HttpServletRequest request) {

        //定义一个企业id
        String companyId = getLoginCompanyId();
        //调用业务进行查询所有部门信息
        List<Dept> list = deptService.findAllByDeptId(companyId);
        //存到请求域中
        request.setAttribute("deptList",list);
        //返回
        return "system/dept/dept-add";
    }


    /**
     * 作用：进入修改页面
     * 路径：/system/dept/toUpdate.do
     * 参数：Dept
     * 返回："system/dept/dept-update"
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(HttpServletRequest request,Dept dept) {

        //定义一个企业id
        String companyId = getLoginCompanyId();
        //调用业务进行查询所有部门信息
        List<Dept> list = deptService.findAllByDeptId(companyId);
        //存到请求域中
        request.setAttribute("deptList",list);
        //返回
        return "system/dept/dept-update";
    }



    /**
     * 作用：保存或修改部门信息
     * 路径：/system/dept/toUpdate.do
     * 参数：Dept
     * 返回："system/dept/dept-list"
     */
    @RequestMapping("/edit")
    public String edit(Dept dept) {

        //自定义企业id与企业名称，并保存
        String companyId=getLoginCompanyId();
        String companyName =  getLoginCompanyId();
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        //调用业务方法进行添加
        if (StringUtils.isEmpty(dept.getId())) {
            deptService.save(dept);
        }else{
            deptService.update(dept);
        }
        return "redirect:/system/dept/list.do";
    }


    /**
     * 作用：删除部门信息
     * 路径：/system/dept/delete.do?id=4028827c4fb633bd014fb64467470000
     * 参数：id
     * 返回："redirect:/system/dept/list.do"
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String id) {

            //1. 删除部门
            boolean flag=deptService.delete(id);
            //2.判断是否删除成功
            Map<String, Object> map = new HashMap<>();
            map.put("flag", flag);
            if (flag) {
                map.put("message", "删除成功");
            } else {
                map.put("message", "有桃子不能删除");
            }
            return map;
        }
    }
