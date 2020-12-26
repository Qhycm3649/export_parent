package com.xyou.service.system;

import com.github.pagehelper.PageInfo;
import com.xyou.domain.system.User;

import java.util.List;

//用户管理的业务接口
public interface UserService {

    //分页查询全部
    PageInfo<User> findAll(Integer pageNum, Integer pageSize, String companyId);

    //保存
    void save(User user);

    //修改
    void update(User user);

    //根据id进行查询用户
    User findByUserId(String id);

    //查找所有用户
    List<User> findAllUsers(String id);

    // 删除用户
    boolean delete(String id);

    //通过邮箱查询用户
    User findUserByEmail(String email);

    //保存修改用户的角色
    void changeRole(String userid, String[] roleIds);
}
