package com.xyou.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.system.ModuleDao;
import com.xyou.domain.system.Module;
import com.xyou.domain.system.User;
import com.xyou.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {


    //实例Dao对象
    @Autowired
    private ModuleDao moduleDao;


    /**
     * 分页查询所有用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Module> findAll(Integer pageNum, Integer pageSize) {

        //设置起始页和页面大小
        PageHelper.startPage(pageNum, pageSize);
        //执行sql查询方法
        List<Module> moduleList = moduleDao.findAllModule();
        //创建pageInfo对象
        PageInfo<Module> pageInfo = new PageInfo<>(moduleList);
        //返回
        return pageInfo;
    }


    /**
     * 保存用户信息
     * @param module
     */
    @Override
    public void save(Module module) {

        //设置id
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);

    }


    /**
     * 修改用户信息
     * @param module
     */
    @Override
    public void update(Module module) {

        moduleDao.update(module);
    }


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Override
    public Module findByModuleId(String id) {

        return moduleDao.findByModuleId(id);
    }

    @Override
    public List<Module> findAllModules() {
        return moduleDao.findAllModule();
    }

    //删除用户
    @Override
    public boolean delete(String id) {
        moduleDao.delete(id);
        return true;
    }

    //根据用户进行查询模块功能
    @Override
    public List<Module> findModuleByUser(User dbUser) {
        //1. 获取用户的等级
        Integer degree = dbUser.getDegree();
        List<Module> moduleList = null;
        if (degree == 0 || degree == 1) {
            //2.如果是管理员级别是degree=belong这种查询
            moduleList = moduleDao.findModuleByDegree(degree);
        } else {
            //3. 如果是普通的用户需要根据用户的角色去查询
            moduleList = moduleDao.findModuleByUserId(dbUser.getId());
        }
        return moduleList;
    }


    //根据角色id查询当前角色所有的权限
    @Override
    public List<String> findRoleModuleByRoleId(String roleid) {
        return moduleDao.findRoleModuleByRoleId(roleid);
    }
}
