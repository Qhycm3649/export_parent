package com.xyou.dao.cargo;

import com.xyou.domain.cargo.Factory;
import com.xyou.domain.cargo.FactoryExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class FactoryTest {

    @Autowired
    private FactoryDao factoryDao;

    @Test
    public void test1() {
        Factory factory = new Factory();
        factory.setId(UUID.randomUUID().toString());
        factory.setFactoryName("传智IT工厂");
        factory.setCreateTime(new Date());
        factory.setUpdateTime(new Date());
    }

    @Test
    public void test02() {
        FactoryExample factoryExample = new FactoryExample();
        //正在的查询条件应该封装Example里面的Criteria对象中
        FactoryExample.Criteria criteria = factoryExample.createCriteria();
        //封装查询条件
        criteria.andCtypeEqualTo("货物");
        criteria.andFullNameLike("祁县%");

        List<Factory> factoryList = factoryDao.selectByExample(factoryExample);
        System.out.println("查询到的对象："+ factoryList);
}}
