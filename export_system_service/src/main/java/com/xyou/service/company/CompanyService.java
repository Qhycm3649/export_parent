package com.xyou.service.company;

import com.xyou.domain.company.Company;

import java.util.List;

public interface CompanyService {

    //查找所有的关联公司
    List<Company> findAll();
}
