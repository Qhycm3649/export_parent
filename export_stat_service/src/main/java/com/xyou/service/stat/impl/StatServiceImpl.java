package com.xyou.service.stat.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xyou.dao.stat.StatDao;
import com.xyou.service.stat.StatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatDao statDao;


    //获取厂家的销售数据
    @Override
    public List<Map<String, Object>> getFactoryData(String companyId) {
        return statDao.getFactoryData(companyId);
    }
}
