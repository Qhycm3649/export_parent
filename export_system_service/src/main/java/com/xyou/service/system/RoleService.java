package com.xyou.service.system;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.Role;

import java.util.List;

//角色管理业务层接口
public interface RoleService {

    //查询所有角色
    PageInfo<Role> findAll(Integer pageNum,Integer pageSize,String companyId);

    //保存角色
    void save(Role role);

    //修改角色
    void update(Role role);

    //通过id查询角色
    Role findRoleId(String id);

    //通过id查询所有的角色
    List<Role> findAllRole(String companyId);

    //删除
    void delete(String id);

    //保存修改角色权限
    void updateRoleModule(String roleid,String[] moduleIds);

    //根据用户的id查找当前用户具备的角色id
    List<String> findUserRoleByUserId(String id);

    /*//根据角色id进行查找角色
    Role findByRoleId(String roleid);*/
}
