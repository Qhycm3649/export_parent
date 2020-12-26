package com.xyou.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.system.RoleDao;
import com.xyou.domain.system.Role;
import com.xyou.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

//业务层的实例接口类
@Service
public class RoleServiceImpl implements RoleService {

    //实例DAO层
    @Autowired
    private RoleDao roleDao;

    //查询所有角色
    @Override
    public PageInfo<Role> findAll(Integer pageNum,Integer pageSize,String companyId) {

        //设置页面的大小和当前页
        PageHelper.startPage(pageNum, pageSize);
        //调用方法查询
        List<Role> list = roleDao.findAll(companyId);
        //创建pageInfo对象
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    //保存角色
    @Override
    public void save(Role role) {

        //设置id
        role.setId(UUID.randomUUID().toString());
        roleDao.save(role);
    }


    //修改角色
    @Override
    public void update(Role role) {

        roleDao.update(role);
    }

    @Override
    public Role findRoleId(String id) {

        return roleDao.findById(id);
    }

    @Override
    public List<Role> findAllRole(String companyId) {

        List<Role> roleList = roleDao.findAll(companyId);
        return roleList;
    }

    @Override
    public void delete(String id) {

        roleDao.delete(id);
    }

    //保存修改角色权限
    @Override
    public void updateRoleModule(String roleid, String[] moduleIds) {
        //1.先删除该角色的所有权限
        roleDao.deleteModuleByRoleId(roleid);
        //2.重新为当前的角色添加权限
        roleDao.addRoleModules(roleid,moduleIds);

    }


    //根据用户的id查找当前用户具备的角色id
    @Override
    public List<String> findUserRoleByUserId(String id) {
        return roleDao.findUserRoleByUserId(id);
    }


    //根据角色id进行查找角色
   /* @Override
    public Role findByRoleId(String roleid) {
        return roleDao.findByRoleId(roleid);
    }*/
}
