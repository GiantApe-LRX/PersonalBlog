package com.cubemonkey.personalblog.service.impl;

import com.cubemonkey.personalblog.entity.User;
import com.cubemonkey.personalblog.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * @author CubeMonkey
 * @create 2020-11-23 15:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void findUserById() {
        User result = userService.findUserById(new BigInteger("1"));
        System.out.println(result);
        Assert.assertEquals(new BigInteger("1"), result.getUserId());
    }

    @Test
    public void findUserByUsername() {
    }

    @Test
    public void checkLogin() {
        User user = userService.findUserByUsername("root");
        Boolean result = userService.checkLogin(user, "root");
        Assert.assertEquals(true, result);
    }

    @Test
    public void createUser() {
        User user = new User();
        user.setUsername("root");
        user.setPassword("root");
        User lrx = userService.createUser(user);
        System.out.println(lrx);
        Assert.assertNotNull(lrx);
    }
}