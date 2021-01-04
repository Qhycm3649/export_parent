package com.xyou.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.cargo.*;
import com.xyou.domain.cargo.*;
import com.xyou.service.cargo.ExportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

//报运商品的服务实现类
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExportProductDao exportProductDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ExtEproductDao extEproductDao;

    //报运单的分页列表
    @Override
    public PageInfo<Export> findByPage(ExportExample exportExample, int pageNum, int pageSize) {
        //1. 设置开始页与页面大小
        PageHelper.startPage(pageNum, pageSize);
        //2. 设置所有
        List<Export> list = exportDao.selectByExample(exportExample);
        //3 /.构建一个PageInfo
        PageInfo<Export> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //根据条件查询全部的报运单
    @Override
    public List<Export> findAll(ExportExample exportExample) {
        return exportDao.selectByExample(exportExample);
    }

    //根据id查找报运单
    @Override
    public Export findById(String id) {
        Export export = exportDao.selectByPrimaryKey(id);
        return export;
    }

    //添加报运单
    @Override
    public void save(Export export) {
        //设置主键
        export.setId(UUID.randomUUID().toString());
        //给报运单补充数据
        export.setCreateTime(new Date());
        export.setUpdateTime(new Date());
        //查出该报运单对应的购销合同
        String[] contractIds = export.getContractIds().split(",");//去除该报运单对应的购物合同的id
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andIdIn(Arrays.asList(contractIds));
        List<Contract> contractList = contractDao.selectByExample(contractExample);

        //遍历购销合同
        String contractNos = "";  //定义变量保存所有的合同号
        Integer totalProNum = 0;  //定义货物的种类数量
        Integer totalExtNum = 0;  //定义附件的种类数量
        for (Contract contract : contractList) {

            contractNos += contract.getContractNo() + " ";
            totalProNum += contract.getProNum();
            totalExtNum += contract.getExtNum();
            //2 报运单下面的购销合同的状态从1改为2，（购销合同的状态：0 代表草稿，1代表允许被报运，3代表已经生成了报运单合同）
            contract.setState(2);
            contractDao.updateByPrimaryKeySelective(contract);
        }
        ////给报运单设置合同号
        export.setCustomerContract(contractNos);
        export.setState(0);
        //设置货物的数量
        export.setProNum(totalProNum);
        export.setExtNum(totalExtNum);
        //设置创建事件与修改事件
        export.setCreateTime(new Date());
        export.setUpdateTime(new Date());
        //插入
        exportDao.insertSelective(export);

        //3 把购销合同下的所有的货物数据导入到报运单商品中
        //找到购销合同下的货物数据
        ContractProductExample contractProductExample = new ContractProductExample();
        //添加条件：购销合同下的货物
        contractProductExample.createCriteria().andContractIdIn(Arrays.asList(contractIds));
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);
        //定义一个Map集合存储购销合同货物的id与报运商品的id
        Map<String, String> map = new HashMap<>();//keu:购销合同的货物id，  value：报运单商品的id
        //遍历所有的购销合同的货物
        for (ContractProduct contractProduct : contractProductList) {
            //一个购销合同的货物就是一个报运商品
            ExportProduct exportProduct = new ExportProduct();
            //属性数据拷贝
            BeanUtils.copyProperties(contractProduct,exportProduct);
            //补充报运商品的id
            exportProduct.setId(UUID.randomUUID().toString());
            //补充改报运商品所属的报运单
            exportProduct.setExportId(export.getId());
            //把购销合同的货物id与报运商品的id存储起来
            map.put(contractProduct.getId(), exportProduct.getId());
            //插入报运商品的数据
            exportProductDao.insertSelective(exportProduct);
        }

        //4 把购销合同下的所有的货物的附件数据导入到报运商品的附件表中
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractIdIn(Arrays.asList(contractIds));
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        //遍历购销合同的附件
        for (ExtCproduct extCproduct : extCproductList) {
            //一个购销合同附件对应一个报运的附件
            ExtEproduct extEproduct = new ExtEproduct();
            //属性数据拷贝
            BeanUtils.copyProperties(extCproduct, extEproduct);
            //补充数据： 主键
            extEproduct.setId(UUID.randomUUID().toString());
            //该报运单的附件所属的报运单
            extEproduct.setExportId(export.getId());
            //报运附件所属的报运商品的id
            extEproduct.setExportProductId(map.get(extCproduct.getContractProductId()));
            extEproductDao.insertSelective(extEproduct);
        }
    }

    //更新报运单
    @Override
    public void update(Export export) {
        //更新报运单的信息
        export.setUpdateTime(new Date());
        exportDao.updateByPrimaryKeySelective(export);
        //更新报运单的商品信息
        List<ExportProduct> exportProducts = export.getExportProducts();
        if (exportProducts != null) {
            for (ExportProduct exportProduct : exportProducts) {
                //更新每一个报运商品数据
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    //根据主键删除报运单
    @Override
    public void delete(String id) {
        exportDao.deleteByPrimaryKey(id);
    }


}