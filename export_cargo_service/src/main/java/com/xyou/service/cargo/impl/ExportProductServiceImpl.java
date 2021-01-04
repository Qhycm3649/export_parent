package com.xyou.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.cargo.ExportProductDao;
import com.xyou.domain.cargo.ExportProduct;
import com.xyou.domain.cargo.ExportProductExample;
import com.xyou.service.cargo.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//报运商品的服务实现类
@Service
public class ExportProductServiceImpl implements ExportProductService {

    @Autowired
    private ExportProductDao exportProductDao;


    //报运商品的分页列表
    @Override
    public PageInfo<ExportProduct> findByPage(ExportProductExample exportProductExample, int pageNum, int pageSize) {
        //1. 设置开始页与页面大小
        PageHelper.startPage(pageNum,pageSize);
        //2. 设置所有
        List<ExportProduct> list = exportProductDao.selectByExample(exportProductExample);
        //3 /.构建一个PageInfo
        PageInfo<ExportProduct> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //根据条件查询全部的报运商品
    @Override
    public List<ExportProduct> findAll(ExportProductExample exportProductExample) {
        return exportProductDao.selectByExample(exportProductExample);
    }

    //根据id查找报运商品
    @Override
    public ExportProduct findById(String id) {
        ExportProduct exportProduct = exportProductDao.selectByPrimaryKey(id);
        return exportProduct;
    }

    //添加报运商品
    @Override
    public void save(ExportProduct exportProduct) {
        //设置主键
        exportProduct.setId(UUID.randomUUID().toString());
        exportProduct.setCreateTime(new Date());
        exportProduct.setUpdateTime(new Date());
        exportProductDao.insertSelective(exportProduct);
    }

    //更新报运商品
    @Override
    public void update(ExportProduct exportProduct) {
        exportProduct.setUpdateTime(new Date());
        exportProductDao.updateByPrimaryKeySelective(exportProduct);
    }

    //根据主键删除报运商品
    @Override
    public void delete(String id) {
        exportProductDao.deleteByPrimaryKey(id);
    }

}
