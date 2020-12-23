package com.xyou.test;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.company.CompanyDao;
import com.xyou.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class CompanyTest {

    @Autowired
    private CompanyDao companyDao;

    @Test
    public void test01() {
        List<Company> list = companyDao.findAll();
        System.out.println("我回来了");
    }



    @Test
    public void test02(){
        //1. 设置当前页与页面大小
        PageHelper.startPage(1,3);

        //2. 查询全部的数据,只不过是想得到你的sql语句，因为最终它需要帮你拼接分页的sql语句  select * from xx
        List<Company> list = companyDao.findAll();

        //3. 创建PageInfo对象，PageInfo对象就是你们以前编写的PageBean
        PageInfo<Company> pageInfo = new PageInfo<>(list);


        System.out.println("当前页："+ pageInfo.getPageNum());
        System.out.println("总页数："+ pageInfo.getPages());
        System.out.println("总记录数："+pageInfo.getTotal() );
        System.out.println("页面大小："+pageInfo.getPageSize() );
        System.out.println("页面数据："+ pageInfo.getList() );
    }

}
