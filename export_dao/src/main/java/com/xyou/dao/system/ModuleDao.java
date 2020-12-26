package com.xyou.dao.system;

import com.xyou.domain.system.Module;
import com.xyou.domain.system.User;

import java.util.List;

//用户Dao
public interface ModuleDao {

    //查询所有的用户
    List<Module> findAllModule();

    //保存
    void save(Module module);

    //修改
    void update(Module module);

    //通过id查询用户
    Module findByModuleId(String moduleId);

    //删除用户
    void delete(String id);

    //根据用户的等级查询对应的模块
    List<Module> findModuleByDegree(Integer degree);

    //根据用户的id查找用户的角色，从而查询用户拥有模块
    List<Module> findModuleByUserId(String id);

    //根据角色id查询当前角色所有的权限
    List<String> findRoleModuleByRoleId(String roleid);
}
