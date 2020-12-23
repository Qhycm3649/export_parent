package com.xyou.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.system.DeptDao;
import com.xyou.domain.system.Dept;
import com.xyou.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {

    //实例Dao
    @Autowired
    private DeptDao deptDao;

    //查询所有的部门
    @Override
    public PageInfo<Dept> findAllByDept(Integer pageNum,Integer pageSize,String companyId) {

        //创建起始页与分页大小
        PageHelper.startPage(pageNum, pageSize);
        //查询所有部门
        List<Dept> list = deptDao.findAllByDept(companyId);
        //创建pageInfo对象
        PageInfo<Dept> pageInfo = new PageInfo<>(list);
        //返回
        return pageInfo;
    }


    //保存部门信息
    @Override
    public void save(Dept dept) {

        //因为数据库设置的id是UUID类型，所以没有自动增长，故需要我们自己设置
        dept.setId(UUID.randomUUID().toString());
        deptDao.save(dept);
    }


    //修改部门信息
    @Override
    public void update(Dept dept) {

        deptDao.update(dept);
    }

    //根据id进行查询部门
    @Override
    public List<Dept> findAllByDeptId(String id) {

        return deptDao.findAllByDept(id);
    }

    //根据id进行删除部门信息
    @Override
    public boolean delete(String id) {

        int count=deptDao.findByParentId(id);
        if (count>0) {
            return false;
        }
        deptDao.delete(id);
        return true;
    }

}
