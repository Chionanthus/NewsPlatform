package com.chionanthus.mapper;


import com.chionanthus.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> queryUserList();

    User queryUserById(int id);

    int addUser(User user);

    int AlterUserPwd(User user);

    String UserLogin(User user);

    int deleteUser(int id);

    List<User> queryUserByName(String queryUserName);

    int queryUserByStrictName(String user_name);

    User queryOneUserInfoByName(String user_name);

    int updateUser(User user);

    String queryUserNameByID(int author_id);

    int queryUserRole(int user_id);

    int switchToAuthorById(int user_id);

    int switchToUserById(int user_id);

    int alterPassword(User user);
}
