package com.xyou.service.company.impl;

import com.xyou.dao.company.CompanyDao;
import com.xyou.domain.company.Company;
import com.xyou.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyServiceImpl implements CompanyService {


    //实例CompanyDao
    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }
}
