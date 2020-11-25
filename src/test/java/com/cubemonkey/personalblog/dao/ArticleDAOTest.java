package com.cubemonkey.personalblog.dao;

import com.cubemonkey.personalblog.entity.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author CubeMonkey
 * @create 2020-11-24 9:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleDAOTest {

    @Autowired
    private ArticleDAO articleDAO;

    @Test
    public void saveTest(){
        Article article = new Article();
        article.setArticleTitle("文章管理系统的测试标题1");
        article.setArticleContent("文章管理系统的测试内容2");
        article.setUserId(new BigInteger("1"));
        Article result = articleDAO.save(article);
        Assert.assertNotNull(result);
    }

    @Test
    public void saveBatchTest(){
        for (int i = 1; i <= 100; i++) {
            Article article = new Article();
            article.setArticleTitle("文章管理系统的测试标题"+i);
            article.setArticleContent("文章管理系统的测试内容"+i);
            article.setUserId(new BigInteger("2"));
            articleDAO.save(article);
        }
    }

    @Test
    public void updateTest(){
        Article article = articleDAO.findByArticleId(new BigInteger("3"));
        article.setArticleContent("文章管理系统的测试内容112312312132");
        Article result = articleDAO.save(article);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByArticleId() {
        Article article = articleDAO.findByArticleId(new BigInteger("1"));
        Assert.assertEquals(new BigInteger("1"), article.getArticleId());
    }

    @Test
    public void deleteByArticleId(){
        Article article = articleDAO.findByArticleId(new BigInteger("1"));
        articleDAO.delete(article);
        article = articleDAO.findByArticleId(new BigInteger("1"));
        Assert.assertNull(article);
    }

    @Test
    public void findByRequire() {
        PageRequest request = PageRequest.of(0, 100);
        Page<Article> articlePage = articleDAO.findByRequire("测试标题", "2020-11-24 11:00:06", "2020-11-24 11:00:08", new BigInteger("6"), request);
        Assert.assertEquals(100, articlePage.getTotalElements());
    }
}