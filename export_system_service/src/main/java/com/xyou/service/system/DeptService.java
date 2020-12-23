package com.xyou.service.system;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.Dept;

import java.util.List;

/**
 * @author ming
 */
//系统管理部门业务接口
public interface DeptService {

    //查询所有的部门
    PageInfo<Dept> findAllByDept(Integer pageNum,Integer pageSize,String companyId);

    //保存部门信息
    void save(Dept dept);

    //修改部门信息
    void update(Dept dept);

    //根据id查询所有部门
    List<Dept> findAllByDeptId(String id);

    //根据id进行删除
    boolean delete(String id);

}
