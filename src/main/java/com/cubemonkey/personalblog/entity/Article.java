package com.cubemonkey.personalblog.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Date;


/**
 * @author CubeMonkey
 * @create 2020-11-20 13:42
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*文章编号*/
    private BigInteger articleId;

    /*用户编号*/
    private BigInteger userId;

    /*文章标题*/
    private String articleTitle;

    /*文章内容*/
    private String articleContent;

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private Date updateTime;
}
