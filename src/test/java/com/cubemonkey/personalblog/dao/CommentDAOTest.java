package com.cubemonkey.personalblog.dao;

import com.cubemonkey.personalblog.entity.Comment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author CubeMonkey
 * @create 2020-11-24 22:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentDAOTest {
    @Autowired
    CommentDAO commentDAO;

    @Test
    public void publishTest(){
        Comment comment = new Comment();
        comment.setArticleId(new BigInteger("1"));
        comment.setUserId(new BigInteger("2"));
        comment.setCommentContent("测试创建评论");
        comment.setParentCommentId(new BigInteger("0"));
        Comment result = commentDAO.save(comment);
        Assert.assertNotNull(result);
    }
    @Test
    public void publishBatch(){
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Comment comment = new Comment();
            comment.setArticleId(new BigInteger("1"));
            comment.setUserId(new BigInteger("2"));
            comment.setCommentContent("测试评论"+i);
            comment.setParentCommentId(new BigInteger("0"));
            comments.add(comment);
        }
        for (int i = 5; i < 10; i++) {
            Comment comment = new Comment();
            comment.setArticleId(new BigInteger("1"));
            comment.setUserId(new BigInteger("1"));
            comment.setCommentContent("测试管理员评论"+i);
            comment.setParentCommentId(new BigInteger("1"));
            comments.add(comment);
        }
        List<Comment> result = commentDAO.saveAll(comments);
        Assert.assertEquals(10, result.size());
    }

    @Test
    public void updateTest(){
        Comment comment = commentDAO.findByCommentId(new BigInteger("1"));
        comment.setCommentContent("测试修改评论");
        Comment result = commentDAO.save(comment);
        Assert.assertNotNull(result);
    }

    @Test
    public void deleteTest(){
        commentDAO.deleteById(new BigInteger("1"));
        Assert.assertNull(commentDAO.findByCommentId(new BigInteger("1")));
    }
    @Test
    public void findByCommentId(){
        Comment comment = commentDAO.findByCommentId(new BigInteger("1"));
        System.out.println(comment);
        Assert.assertEquals(new BigInteger("1"), comment.getCommentId());
    }

    @Test
    public void findByArticleId(){
        List<Comment> comments = commentDAO.findByArticleId(new BigInteger("1"));
        Assert.assertEquals(10, comments.size());
    }
}