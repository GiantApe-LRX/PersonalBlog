package com.cubemonkey.personalblog.service.impl;

import com.cubemonkey.personalblog.entity.Article;
import com.cubemonkey.personalblog.service.ArticleService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.reactive.AbstractReactiveTransactionManager;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * @author CubeMonkey
 * @create 2020-11-24 13:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceImplTest {

    @Autowired
    ArticleService articleService;

    @Test
    public void publish() {
        Article article = new Article();
        article.setUserId(new BigInteger("7"));
        article.setArticleTitle("service创建测试标题");
        article.setArticleContent("service创建内容");
        articleService.publish(article);
    }

    @Test
    public void update() {
        Article article = articleService.findByArticleId(new BigInteger("102"));
        article.setArticleTitle("文章service更新测试标题");
        article.setArticleContent("文章service更新测试内容");
        articleService.update(article);
    }

    @Test
    public void findArticleByRequire() {
    }

    @Test
    public void findArticleByArticleId() {
    }

    @Test
    public void delete() {
    }
}