package com.xyou.dao.system;

import com.xyou.domain.system.Dept;

import java.util.List;

/**
 * @author ming
 */
//持久层Dao系统管理接口
public interface DeptDao {

    //查询所有的部门
    //-- 查询所有部门, 部门是属于某家公司特有的，部门不能共享。
    //-- 企业id是属于当前登陆者所属企业的id
    List<Dept> findAllByDept(String companyId);


    //通过id查找部门
    Dept findDeptById(String id);


    //保存部门信息
    void save(Dept dept);


    //修改部门信息
    void update(Dept dept);


    //根据id进行删除部门信息
    void delete(String id);

    int findByParentId(String id);
}
