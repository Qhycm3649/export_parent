package com.xyou.web.controller.company;

import com.xyou.domain.company.Company;
import com.xyou.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {


    @Autowired
    private CompanyService companyService;

    @RequestMapping("/find")
    public String findAll(Model model) {

        List<Company> companyList = companyService.findAll();
        model.addAttribute("companyList", companyList);
        return "company/company-list";
    }

    //测试传输String的日期
    @RequestMapping("/save")
    public String save(Date date) {
        System.out.println("我的小白狼="+date);
        return "success";
    }
}
