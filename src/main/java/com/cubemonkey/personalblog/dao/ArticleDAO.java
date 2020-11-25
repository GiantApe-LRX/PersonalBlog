package com.cubemonkey.personalblog.dao;

import com.cubemonkey.personalblog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

/**
 * @author CubeMonkey
 * @create 2020-11-24 8:37
 */

public interface ArticleDAO extends JpaRepository<Article, BigInteger> {

    /*多条件分页查询*/
    @Query(value = "select * from article where 1 = 1 " +
            "AND IF('' != :articleTitle, article_title like %:articleTitle%, 1=1) " +
            "AND IF('' != :createTimeMin, create_time >= :createTimeMin, 1=1) " +
            "AND IF('' != :createTimeMax, create_time <= :createTimeMax, 1=1) " +
            "AND IF('' != :authorId, user_id = :authorId, 1=1) ", nativeQuery = true)
    Page<Article> findByRequire(@Param("articleTitle") String articleTitle,
                                @Param("createTimeMin") String createTimeMin,
                                @Param("createTimeMax") String createTimeMax,
                                @Param("authorId") BigInteger authorId, Pageable pageable);

    /*单篇文章查询*/
    Article findByArticleId(BigInteger articleId);

}
