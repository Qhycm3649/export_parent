package com.xyou.web.controller.company;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.xyou.domain.company.Company;
import com.xyou.service.company.CompanyService;
import com.xyou.web.controller.system.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {


    @Reference
    private CompanyService companyService;


    @RequestMapping("/list")
    public String findAll(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, HttpServletRequest request) {

        PageInfo<Company> pageInfo = companyService.findByPage(pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "company/company-list";
    }


    //测试传输String的日期
    /*@RequestMapping("/save")
    public String save(Date date) {
        System.out.println("我的小白狼=" + date);
        return "success";
    }*/


    //按新建进入添加页面
    @RequestMapping("/toAdd")
    public String toAdd() {
        //作用：加载home/home页面进内容区域
        return "company/company-add";
    }


    //添加合作公司
    @RequestMapping("/edit")
    public String edit(Company company) {

        if (StringUtils.isEmpty(company.getId())) {
            //调用业务层添加方法
            companyService.saves(company);
        } else {
            //修改
            companyService.update(company);

        }
        //跳转最新查询列表
        return "redirect:/company/list.do";

    }


    //按新建进入修改页面
    @RequestMapping("/toUpdate")
    public String findById(String id, HttpServletRequest request) {

        //调用业务层执行toUpdate操作
        Company company = companyService.findById(id);

        //将查询结果，保存到请求域中
        request.setAttribute("company", company);

        //跳转到修改页面
        return "company/company-update";
    }


    //按id进行删除
    @RequestMapping("/delete")
    public String delete(String id) {

        //调用业务进行删除
        companyService.delete(id);

        return "redirect:/company/list.do";
    }

}
