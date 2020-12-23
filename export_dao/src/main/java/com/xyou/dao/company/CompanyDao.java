package com.xyou.dao.company;

import com.xyou.domain.company.Company;

import java.util.List;

public interface CompanyDao {

    //保存修改
    void update(Company company);

    //查询所有的关联公司
    List<Company> findAll();

    //添加关联公司
    void saves(Company company);

    //查询一个目标公司
    Company findById(String id);

    //删除公司
    void delete(String id);

}
