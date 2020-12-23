package com.xyou.service.company.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.company.CompanyDao;
import com.xyou.domain.company.Company;
import com.xyou.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {


    //实例CompanyDao
    @Autowired
    private CompanyDao companyDao;


    //查找所有公司
    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }


    //添加一个合作公司
    @Override
    public void saves(Company company) {
        company.setId(UUID.randomUUID().toString());
        companyDao.saves(company);
    }


    //修改一个合作公司
    @Override
    public void update(Company company) {
        companyDao.update(company);
    }


    //分页
    @Override
    public PageInfo<Company> findByPage(Integer pageNum, Integer pageSize) {
        //设置当前页与页面大小
        PageHelper.startPage(pageNum, pageSize);
        //查询的sql语句
        List<Company> list = companyDao.findAll();
        //创建pageInfo对象
        PageInfo<Company> pageInfo = new PageInfo<>(list);
        //返回
        return pageInfo;
    }


    //删除一个公司
    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }


    //查找一个目标公司
    @Override
    public Company findById(String id) {
        Company companyDaoById = companyDao.findById(id);
        return companyDaoById;
    }


}
