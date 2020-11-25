package com.cubemonkey.personalblog.service.impl;

import com.cubemonkey.personalblog.dao.ArticleDAO;
import com.cubemonkey.personalblog.dao.CommentDAO;
import com.cubemonkey.personalblog.dao.UserDAO;
import com.cubemonkey.personalblog.entity.Article;
import com.cubemonkey.personalblog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

/**
 * @author CubeMonkey
 * @create 2020-11-24 12:02
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public void publish(Article article) {
        articleDAO.save(article);
    }

    @Override
    public void update(Article article) {
        articleDAO.save(article);
    }

    @Override
    public Page<Article> findByRequire(String articleTitle, String createTimeMin, String createTimeMax, BigInteger authorId, Pageable pageable) {
        return articleDAO.findByRequire(articleTitle, createTimeMin, createTimeMax, authorId, pageable);
    }


    @Override
    public Article findByArticleId(BigInteger articleId) {
        return articleDAO.findByArticleId(articleId);
    }

    @Override
    @Transactional
    public void delete(BigInteger articleId) {
        //删除该文章下的所有评论
        commentDAO.deleteCommentByArticleId(articleId);
        articleDAO.deleteById(articleId);
    }
}
