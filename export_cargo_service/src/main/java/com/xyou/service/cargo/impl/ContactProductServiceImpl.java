package com.xyou.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.cargo.ContractDao;
import com.xyou.domain.cargo.Contract;
import com.xyou.domain.cargo.ContractExample;
import com.xyou.service.cargo.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//购销合同的实现接口实现类
@Service
public class ContactProductServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;


    @Override
    public PageInfo<Contract> findByPage(ContractExample contractExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Contract> contracts = contractDao.selectByExample(contractExample);
        PageInfo<Contract> pageInfo = new PageInfo<>(contracts);
        return pageInfo;
    }

    @Override
    public List<Contract> findAll(ContractExample contractExample) {
        return contractDao.selectByExample(contractExample);
    }

    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Contract contract) {
        contract.setId(UUID.randomUUID().toString());
        contract.setCreateTime(new Date());
        contract.setUpdateTime(new Date());
        contractDao.insertSelective(contract);
    }

    @Override
    public void update(Contract contract) {
        contract.setUpdateTime(new Date());
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        contractDao.deleteByPrimaryKey(id);
    }
}
