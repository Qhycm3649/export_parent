package com.xyou.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xyou.domain.company.Company;
import com.xyou.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplyController {

    @Reference
    private CompanyService companyService;

    @RequestMapping("/apply.do")
    @ResponseBody
    public String apply(Company company) {

        //企业刚注册进软件的时候，状态为0，
        try {
            company.setState(0);
            companyService.saves(company);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}
