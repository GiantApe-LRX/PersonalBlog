package com.cubemonkey.personalblog.service.impl;

import com.cubemonkey.personalblog.dao.UserDAO;
import com.cubemonkey.personalblog.entity.User;
import com.cubemonkey.personalblog.enums.ResultEnum;
import com.cubemonkey.personalblog.exception.BlogException;
import com.cubemonkey.personalblog.service.UserService;
import com.cubemonkey.personalblog.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.xml.transform.Result;
import java.math.BigInteger;
import java.util.Random;

/**
 * @author CubeMonkey
 * @create 2020-11-20 14:25
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Override
    public User findUserById(BigInteger userId) {
        return userDAO.findByUserId(userId);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * 登录验证
     * @param user 用户名
     * @param password
     * @return
     */
    @Override
    public Boolean checkLogin(User user, String password) {
        String pwd = MD5Utils.encodeMD5(password, user.getSalt());
        return user.getPassword().equals(pwd);
    }


    @Override
    public User createUser(User user) {
        /*随机生成盐值*/
        Integer salt = (int) (Math.random()*100);
        user.setSalt(salt);
        /*用md5对password进行加密*/
        String pwd = MD5Utils.encodeMD5(user.getPassword(), salt);
        user.setPassword(pwd);
        /*保存user*/
        return userDAO.save(user);
    }
}
