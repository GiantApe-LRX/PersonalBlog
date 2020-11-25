package com.cubemonkey.personalblog.service;

import com.cubemonkey.personalblog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

/**
 * 文章管理系统
 * @author CubeMonkey
 * @create 2020-11-24 11:41
 */
public interface ArticleService {
    /*发布文章*/
    void publish(Article article);

    /*修改文章*/
    void update(Article article);

    /*多条件分页列表查询*/
    Page<Article> findByRequire(String articleTitle, String createTimeMin, String createTimeMax, BigInteger authorId, Pageable pageable);

    /*单篇文章查询*/
    Article findByArticleId(BigInteger articleId);

    /*删除文章*/
    void delete(BigInteger articleId);
}
