package com.chionanthus.service;

import com.chionanthus.entity.User;

import java.util.List;

public interface UserService {

    List<User> queryUserList();

    User queryUserById(int id);

    int addUser(User user);

    int AlterUserPwd(User user);

    String UserLogin(User user);

    int deleteUser(int id);

    List<User> queryUserByName(String queryUserName);

    int queryUserByStrictName(String username);

    User queryOneUserInfoByName(String username);

    int updateUser(User user);

    String queryUserNameByID(int author_id);

    int switchRole(int user_id);

    int alterPassword(User user);
}
