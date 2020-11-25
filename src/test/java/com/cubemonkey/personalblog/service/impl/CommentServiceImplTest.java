package com.cubemonkey.personalblog.service.impl;

import com.cubemonkey.personalblog.entity.Comment;
import com.cubemonkey.personalblog.service.CommentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author CubeMonkey
 * @create 2020-11-25 8:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;

    @Test
    public void findByArticleId() {
        List<Comment> articles = commentService.findByArticleId(new BigInteger("1"));
        Assert.assertEquals(10, articles.size());
    }

    @Test
    public void publish() {
        Comment comment = new Comment();
        comment.setArticleId(new BigInteger("1"));
        comment.setUserId(new BigInteger("2"));
        comment.setCommentContent("评论service发布测试");
        comment.setParentCommentId(new BigInteger("0"));
        commentService.publish(comment);
    }

    @Test
    public void update() {
        Comment comment = commentService.findByCommentId(new BigInteger("12"));
        comment.setCommentContent("评论service更新测试");
        commentService.update(comment);
    }

    @Test
    public void delete() {
        commentService.delete(new BigInteger("12"));
    }
}