package com.xyou.service.company;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.company.Company;

import java.util.List;

public interface CompanyService {

    //查找所有的关联公司
    List<Company> findAll();

    //添加合作公司
    void saves(Company company);

    //修改合作公司
    void update(Company company);

    //通过id，进行查找目标公司
    Company findById(String id);

    //删除公司
    void delete(String id);

    //定义分页查询
    PageInfo<Company> findByPage(Integer pageNum, Integer pageSize);
}
