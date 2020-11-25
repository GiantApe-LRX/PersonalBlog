package com.cubemonkey.personalblog.dao;

import com.cubemonkey.personalblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * @author CubeMonkey
 * @create 2020-11-24 22:20
 */
public interface CommentDAO extends JpaRepository<Comment, BigInteger> {

    /*根据评论编号查询*/
    Comment findByCommentId(BigInteger commentId);

    /*根据文章编号查询*/
    List<Comment> findByArticleId(BigInteger articleId);

    /*根据父级编号查询*/
    List<Comment> findByParentCommentId(BigInteger parentCommentId);

    /*删除指定评论以及其下的所有评论*/
    void deleteCommentByCommentId(BigInteger commentId);

    /*删除该文章下的所有评论*/
    void deleteCommentByArticleId(BigInteger articleId);
}
