package com.xyou.dao.company;

import com.xyou.domain.company.Company;

import java.util.List;

public interface CompanyDao {
    //查询所有的关联公司
    List<Company> findAll();
}
