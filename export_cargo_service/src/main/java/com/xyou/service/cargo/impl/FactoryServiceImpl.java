package com.xyou.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.cargo.FactoryDao;
import com.xyou.domain.cargo.Factory;
import com.xyou.domain.cargo.FactoryExample;
import com.xyou.service.cargo.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//工厂的实现接口实现类
@Service
public class FactoryServiceImpl implements FactoryService {

    //依赖注入
    @Autowired
    private FactoryDao factoryDao;

    /*
        工厂分页查询
     */
    @Override
    public PageInfo<Factory> findByPage(FactoryExample factoryExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Factory> factorys = factoryDao.selectByExample(factoryExample);
        PageInfo<Factory> pageInfo = new PageInfo<>(factorys);
        return pageInfo;
    }

    /*
       工厂条件查询
    */
    @Override
    public List<Factory> findAll(FactoryExample factoryExample) {
        return factoryDao.selectByExample(factoryExample);
    }

    /*
       根据id查询工厂
   */
    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    /*
      添加工厂
    */
    @Override
    public void save(Factory factory) {
        factory.setId(UUID.randomUUID().toString());
        factory.setCreateTime(new Date());
        factory.setUpdateTime(new Date());
        factoryDao.insertSelective(factory);
    }

    /*
        更新工厂
    */
    @Override
    public void update(Factory factory) {
        factory.setUpdateTime(new Date());
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    /*
        根据主键删除工厂
     */
    @Override
    public void delete(String id) {
        factoryDao.deleteByPrimaryKey(id);
    }

    @Override
    public Factory findByFactoryName(String factoryName) {
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andFactoryNameEqualTo(factoryName);
        List<Factory> factoryList = factoryDao.selectByExample(factoryExample);
        if (factoryList.size() > 0) {
            return factoryList.get(0);
        }
        return null;
    }
}
