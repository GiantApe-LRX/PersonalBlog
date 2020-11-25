package com.cubemonkey.personalblog.dto;

import com.cubemonkey.personalblog.entity.Comment;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;


/**
 * @author CubeMonkey
 * @create 2020-11-24 17:32
 */

@Data
public class ArticleDetailDTO {
    //文章编号
    private BigInteger articleId;

    //作者
    private String author;

    //文章标题
    private String articleTitle;

    //文章内容
    private String articleContent;

    //创建时间
    private Long createTime;

    //更新时间
    private Long updateTime;

    /*评论信息*/
    private List<Comment> comments;

}