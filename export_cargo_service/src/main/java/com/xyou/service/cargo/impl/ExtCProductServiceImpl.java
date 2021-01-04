package com.xyou.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.cargo.ContractDao;
import com.xyou.dao.cargo.ExtCproductDao;
import com.xyou.domain.cargo.Contract;
import com.xyou.domain.cargo.ExtCproduct;
import com.xyou.domain.cargo.ExtCproductExample;
import com.xyou.service.cargo.ExtCproductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//附件的实现接口实现类
@Service
public class ExtCProductServiceImpl implements ExtCproductService {

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;

    //分页
    @Override
    public PageInfo<ExtCproduct> findByPage(ExtCproductExample extCproductExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
        PageInfo<ExtCproduct> pageInfo = new PageInfo<>(extCproducts);
        return pageInfo;
    }
    //通过附件查找全部
    @Override
    public List<ExtCproduct> findAll(ExtCproductExample extCproductExample) {
        return extCproductDao.selectByExample(extCproductExample);
    }

    //通过id查找对应附件
    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    //添加
    @Override
    public void save(ExtCproduct extCproduct) {

        extCproduct.setId(UUID.randomUUID().toString());
        extCproduct.setCreateTime(new Date());
        extCproduct.setUpdateTime(new Date());

        //1.计算附件总价（单价*数量）
        double amount = 0;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
            extCproduct.setAmount(amount);
        }
        //2.插入一条附件表记录
        extCproductDao.insertSelective(extCproduct);
        //3.计算新合同的总价（原合同总价+附件总价）
        //通过合同id获得合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        //4.计算合同的附件数量（原合同的附件数+1）
        if (contract.getExtNum() != null) {
            contract.setExtNum(contract.getExtNum() + 1);
        } else {
            contract.setExtNum(1);
        }
        //5.更新合同表的数据
        contractDao.updateByPrimaryKeySelective(contract);

        extCproductDao.insertSelective(extCproduct);
    }

    //修改
    @Override
    public void update(ExtCproduct extCproduct) {
        //更新附件之前把附件查询出来
        ExtCproduct oldExtCproduct = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        extCproduct.setUpdateTime(new Date());

       //1.计算附件总价（单价*数量）
        double amount = 0;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
            extCproduct.setAmount(amount);
        }
       //2.更新附件表一条记录
        extCproductDao.insertSelective(extCproduct);
       //3.计算合同的总价（原合同的总价-原附件总价+新附件总价）
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()-oldExtCproduct.getAmount()+amount);
        //4.更新合同表的记录
        contractDao.updateByPrimaryKeySelective(contract);
    }

    //删除
    @Override
    public void delete(String id) {
        //删除附件之前先找到附件
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
       //1.删除附件表的一条记录
        extCproductDao.deleteByPrimaryKey(id);
       //2.计算合同的总价（原合同的总价-附件总价）
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());
        //3.计算合同的附件数量（原附件数量-1）
        contract.setExtNum(contract.getExtNum()-1);
       //4.更新合同表的记录
        contractDao.updateByPrimaryKeySelective(contract);
    }
}
