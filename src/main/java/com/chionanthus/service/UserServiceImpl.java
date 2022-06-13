package com.chionanthus.service;

import com.chionanthus.entity.User;
import com.chionanthus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> queryUserList() {
        return userMapper.queryUserList();
    }

    @Override
    public User queryUserById(int id) {
        return userMapper.queryUserById(id);
    }

    @Override
    public List<User> queryUserByName(String queryUserName) {
        return userMapper.queryUserByName(queryUserName);
    }

    @Override
    public User queryOneUserInfoByName(String username) {
        return userMapper.queryOneUserInfoByName(username);
    }

    @Override
    public int queryUserByStrictName(String user_name) {
        return userMapper.queryUserByStrictName(user_name);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int AlterUserPwd(User user) {
        return userMapper.AlterUserPwd(user);
    }
    public String UserLogin(User user)
    {
        return userMapper.UserLogin(user);
    }

    @Override
    public String queryUserNameByID(int author_id) {
        return userMapper.queryUserNameByID(author_id);
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int switchRole(int user_id) {
        if(userMapper.queryUserRole(user_id)==1)
        {
            return userMapper.switchToAuthorById(user_id);
        }
        else if(userMapper.queryUserRole(user_id)==2)
        {
            return userMapper.switchToUserById(user_id);
        }
        return 0;
    }

    @Override
    public int alterPassword(User user) {
        return userMapper.alterPassword(user);
    }
}
