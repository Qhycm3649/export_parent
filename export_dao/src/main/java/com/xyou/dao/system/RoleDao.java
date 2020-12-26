package com.xyou.dao.system;

import com.xyou.domain.company.Company;
import com.xyou.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//角色管理
public interface RoleDao {

    //查找所有的角色
    List<Role> findAll(String companyId);

    //根据id进行查询
    Role findById(String id);

    //修改角色
    void update(Role role);

    //保存角色
    void save(Role role);

    //删除角色
    void delete(String id);

    void deleteModuleByRoleId(String roleid);

    void addRoleModules(@Param("roleid") String roleid, @Param("moduleIds") String[] moduleIds);


    //根据用户的id查找当前用户具备的角色id
    List<String> findUserRoleByUserId(String id);

    /*//根据角色id进行查找角色
    Role findByRoleId(String roleid);*/
}
