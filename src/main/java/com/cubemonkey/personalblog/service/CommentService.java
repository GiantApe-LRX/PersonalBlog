package com.cubemonkey.personalblog.service;

import com.cubemonkey.personalblog.entity.Comment;

import java.math.BigInteger;
import java.util.List;

/**
 * @author CubeMonkey
 * @create 2020-11-24 22:59
 */
public interface CommentService {
    /*根据文章id查询所有的评论*/
    List<Comment> findByArticleId(BigInteger articleId);

    /*查询单条评论*/
    Comment findByCommentId(BigInteger commentId);

    /*发布评论*/
    void publish(Comment comment);

    /*更新评论*/
    void update(Comment comment);

    /*删除评论*/
    void delete(BigInteger commentId);
}
