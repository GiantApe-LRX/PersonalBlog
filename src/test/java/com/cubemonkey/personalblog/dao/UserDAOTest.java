package com.cubemonkey.personalblog.dao;


import com.cubemonkey.personalblog.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;


/**
 * @author CubeMonkey
 * @create 2020-11-20 13:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOTest {
    @Autowired
    private UserDAO userDAO;

    @Test
    public void findOne(){
        User user = userDAO.findByUserId(new BigInteger("1"));
        Assert.assertEquals(new BigInteger("1"), user.getUserId());
    }

    @Test
    public void save(){
        User user = new User();
        user.setUsername("root");
        user.setPassword("root");
        user.setSalt(123);
        user.setType(1);
        User result = userDAO.save(user);
        Assert.assertNotNull(result);
    }
}