package com.cubemonkey.personalblog.dao;

import com.cubemonkey.personalblog.entity.Article;
import com.cubemonkey.personalblog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

/**
 * @author CubeMonkey
 * @create 2020-11-20 13:51
 */
public interface UserDAO extends JpaRepository<User, BigInteger> {
    User findByUserId(BigInteger userId);

    User findByUsername(String username);

}
