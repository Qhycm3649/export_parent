package com.xyou.service.system;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.Module;
import com.xyou.domain.system.User;

import java.util.List;

//用户管理的业务接口
public interface ModuleService {

    //分页查询全部
    PageInfo<Module> findAll(Integer pageNum, Integer pageSize);

    //保存
    void save(Module module);

    //修改
    void update(Module module);

    //根据id进行查询用户
    Module findByModuleId(String id);

    //查找所有用户
    List<Module> findAllModules();

    // 删除用户
    boolean delete(String id);

    //根据用户进行查询模块功能
    List<Module> findModuleByUser(User dbUser);

    //根据角色id查询当前角色所有的权限
    List<String> findRoleModuleByRoleId(String roleid);
}
