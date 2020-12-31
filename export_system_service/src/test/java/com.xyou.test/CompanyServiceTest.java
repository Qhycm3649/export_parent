package com.xyou.test;

import com.xyou.service.company.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-*.xml")
public class CompanyServiceTest {
    
    
    //实例业务层
   /* @Autowired
    private CompanyService companyService;

    @Test
    public void test01() {
        System.out.println("查询所有用户="+companyService.findAll());
    }*/
}
