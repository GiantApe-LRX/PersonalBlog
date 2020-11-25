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
 * @create 2020-11-20 13:45
 */
@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*评论编号*/
    private BigInteger commentId;

    /*用户编号*/
    private BigInteger userId;

    /*文章编号*/
    private BigInteger articleId;

    /*文章内容*/
    private String commentContent;

    /*父级评论编号，为0则表示评论文章，否则评论指定编号的评论*/
    private BigInteger parentCommentId;

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private Date updateTime;
}
