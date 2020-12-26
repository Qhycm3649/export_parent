package com.xyou.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.system.SysLogDao;
import com.xyou.domain.system.SysLog;
import com.xyou.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SysLogServiceImpl implements SysLogService {

    //实例Dao
    @Autowired
    private SysLogDao sysLogDao;

    //查询所有的日志
    @Override
    public PageInfo<SysLog> findAllBySysLog(Integer pageNum,Integer pageSize,String companyId) {

        //创建起始页与分页大小
        PageHelper.startPage(pageNum, pageSize);
        //查询所有日志
        List<SysLog> list = sysLogDao.findAllBySysLog(companyId);
        //创建pageInfo对象
        PageInfo<SysLog> pageInfo = new PageInfo<>(list);
        //返回
        return pageInfo;
    }


    //保存日志信息
    @Override
    public void save(SysLog sysLog) {

        //因为数据库设置的id是UUID类型，所以没有自动增长，故需要我们自己设置
        sysLog.setId(UUID.randomUUID().toString());
        sysLogDao.save(sysLog);
    }

}
