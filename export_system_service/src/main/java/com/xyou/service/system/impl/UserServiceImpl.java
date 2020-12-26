package com.xyou.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyou.dao.system.UserDao;
import com.xyou.domain.system.User;
import com.xyou.service.system.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    //实例Dao对象
    @Autowired
    private UserDao userDao;


    /**
     * 分页查询所有用户
     * @param pageNum
     * @param pageSize
     * @param companyId
     * @return
     */
    @Override
    public PageInfo<User> findAll(Integer pageNum, Integer pageSize, String companyId) {

        //设置起始页和页面大小
        PageHelper.startPage(pageNum, pageSize);
        //执行sql查询方法
        List<User> userList = userDao.findAllUser(companyId);
        //创建pageInfo对象
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        //返回
        return pageInfo;
    }


    /**
     * 保存用户信息
     * @param user
     */
    @Override
    public void save(User user) {

        //设置id
        user.setId(UUID.randomUUID().toString());
        //对用户的密码进行加盐加密
        /*String md5Password = new Md5Hash(user.getPassword(), user.getEmail()).toString();
        user.setPassword(md5Password);*/
        userDao.save(user);

    }


    /**
     * 修改用户信息
     * @param user
     */
    @Override
    public void update(User user) {

        userDao.update(user);
    }


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Override
    public User findByUserId(String id) {

        return userDao.findByUserId(id);
    }

    @Override
    public List<User> findAllUsers(String id) {
        return userDao.findAllUser(id);
    }

    //删除用户
    @Override
    public boolean delete(String id) {
        //判断用户是否有多个角色
        Integer count=userDao.findUserRoleByUserId(id);
        if (count > 0) {
            return false;
        }
        //3. 如果没有子用户，直接删除
        userDao.delete(id);
        return true;
    }

    //通过邮箱查询用户
    @Override
    public User findUserByEmail(String email) {
        User user=userDao.findUserByEmail(email);
        return user;
    }

    //保存修改用户的角色
    @Override
    public void changeRole(String userid, String[] roleIds) {
        //1. 根据用户的id删除用户的所有角色
        userDao.deleteUserRoleByUserId(userid);
        //2.给用户重新分配角色
        userDao.addUserRoles(userid,roleIds);
    }
}
