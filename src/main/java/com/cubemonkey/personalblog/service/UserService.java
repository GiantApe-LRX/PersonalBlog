package com.cubemonkey.personalblog.service;

import com.cubemonkey.personalblog.entity.User;

import java.math.BigInteger;

/**
 * 用户
 * @author CubeMonkey
 * @create 2020-11-20 14:21
 */

public interface UserService {

    /*通过id查询用户*/
    User findUserById(BigInteger userId);

    /*通过用户名查询用户*/
    User findUserByUsername(String username);

    /*登录验证*/
    Boolean checkLogin(User user, String password);

    /*创建用户*/
    User createUser(User user);
}
