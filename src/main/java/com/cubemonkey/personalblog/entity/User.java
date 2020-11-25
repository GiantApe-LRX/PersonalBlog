package com.cubemonkey.personalblog.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;

/**
 * @author CubeMonkey
 * @create 2020-11-20 13:32
 */
@Entity
@Data
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*用户编号*/
    private BigInteger userId;

    /*用户名*/
    private String username;

    /*密码*/
    private String password;

    /*盐值*/
    private Integer salt;

    /*用户类型，0-普通用户，1-管理员，默认为0*/
    private Integer type;

}
