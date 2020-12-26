package com.xyou.dao.system;

import com.xyou.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//用户Dao
public interface UserDao {

    //查询所有的用户
    List<User> findAllUser(String companyId);

    //保存
    void save(User user);

    //修改
    void update(User user);

    //通过id查询用户
    User findByUserId(String userId);

    //删除用户
    void delete(String id);

    //根据id进行查询用户是否分配了角色
    Integer findUserRoleByUserId(String id);

    //通过邮箱查询用户
    User findUserByEmail(String email);

    // 根据用户的id删除用户的所有角色
    void deleteUserRoleByUserId(String userid);

    //给用户重新分配角色
    void addUserRoles(@Param("userid") String userid, @Param("roleIds") String[] roleIds);

}
