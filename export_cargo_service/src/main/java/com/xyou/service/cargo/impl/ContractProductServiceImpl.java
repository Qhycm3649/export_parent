package com.xyou.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.cargo.ContractDao;
import com.xyou.dao.cargo.ContractProductDao;
import com.xyou.dao.cargo.ExtCproductDao;
import com.xyou.domain.cargo.*;
import com.xyou.service.cargo.ContractProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//购销合同的货物服务实现接口实现类
@Service
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ExtCproductDao extCproductDao;
    /*
        分页查询
     */
    @Override
    public PageInfo<ContractProduct> findByPage(ContractProductExample contractExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContractProduct> contracts = contractProductDao.selectByExample(contractExample);
        PageInfo<ContractProduct> pageInfo = new PageInfo<>(contracts);
        return pageInfo;
    }

    /*
        通过货物服务查询
     */
    @Override
    public List<ContractProduct> findAll(ContractProductExample contractExample) {
        return contractProductDao.selectByExample(contractExample);
    }

    /*
        通过id查询货物服务
     */
    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    /*
        添加
     */
    @Override
    public void save(ContractProduct contractProduct) {
        contractProduct.setId(UUID.randomUUID().toString());
        contractProduct.setCreateTime(new Date());
        contractProduct.setUpdateTime(new Date());

        //1. 计算出货物的总价
        double amount = 0;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount=contractProduct.getCnumber()*contractProduct.getPrice();
            contractProduct.setAmount(amount);
        }

        //2. 插入货物
        contractProductDao.insertSelective(contractProduct);
        //3. 更新购销合同的总价， 总价=购销合同的原总价+本次的货物总价
        //找到购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        if (contract.getTotalAmount() != null) {
            contract.setTotalAmount(contract.getTotalAmount() + amount);
        } else {
            //第一次添加货物
            contract.setTotalAmount(amount);
        }

        //4. 更新购销合同的货物的种类数量 ，原种类数量+1；
        if (contract.getProNum() != null) {
            contract.setProNum((contract.getProNum() + 1));
        } else {
            contract.setProNum(1);
        }
        //5. 更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /*
        修改
     */
    @Override
    public void update(ContractProduct contractProduct) {

        //在更新之前，我们就从数据库中把当前的货物查询出来。
        ContractProduct oldContractProduct = contractProductDao.selectByPrimaryKey(contractProduct.getId());

        contractProduct.setUpdateTime(new Date());

        //1 计算出货物的总价
        double amount = 0;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
            contractProduct.setAmount(amount);
        }
        //2 更新货物
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        //3 更新购销合同的总价 购销合同的总价=购销合同的原总价-之前的货物总价+当前货物的总价
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()-oldContractProduct.getAmount()+amount);

        contractDao.updateByPrimaryKeySelective(contract);
    }

    /*
        //根据主键删除货物
     */
    @Override
    public void delete(String id) {
        //找到要被删除的货物
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        //1 删除货物
        contractProductDao.deleteByPrimaryKey(id);
        //2 删除货物附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        //条件--该货物的附件
        extCproductExample.createCriteria().andContractProductIdEqualTo(id);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        //定义一个变量保存所有的附件的总价
        double totalExtAmount=0;
        if (extCproductList != null) {
            for (ExtCproduct extCproduct : extCproductList) {
                totalExtAmount += extCproduct.getAmount();
                //逐个删除附件
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
        }

        //3 更新购销合同的总价    购销合同的总价=购销合同的原总价-货物的总价-附件的总价
        //找到购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() - contractProduct.getAmount() - totalExtAmount);

        //更新购销合同的货物种类的数量
        contract.setProNum(contract.getProNum() - 1);
        //更新购销合同的附件种类数量    改货物的附件种类个数
        if (contract.getExtNum() != null) {
            contract.setExtNum(contract.getExtNum() - extCproductList.size());
        }
        //更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);

    }
}
