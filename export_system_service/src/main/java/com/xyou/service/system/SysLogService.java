package com.xyou.service.system;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.SysLog;

import java.util.List;

/**
 * @author ming
 */
//系统管理日志业务接口
public interface SysLogService {

    //查询所有的日志
    PageInfo<SysLog> findAllBySysLog(Integer pageNum, Integer pageSize, String companyId);

    //保存日志信息
    void save(SysLog sysLog);

}
