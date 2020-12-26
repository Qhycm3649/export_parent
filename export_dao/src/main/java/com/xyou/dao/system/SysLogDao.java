package com.xyou.dao.system;

import com.xyou.domain.system.SysLog;
import java.util.List;

/**
 * @author ming
 */
//持久层Dao系统管理接口
public interface SysLogDao {

    //查询所有的部门
    //-- 查询所有部门, 部门是属于某家公司特有的，部门不能共享。
    //-- 企业id是属于当前登陆者所属企业的id
    List<SysLog> findAllBySysLog(String companyId);

    //保存部门信息
    void save(SysLog sysLog);

}
