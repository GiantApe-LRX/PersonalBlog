package com.cubemonkey.personalblog.dto;

import com.cubemonkey.personalblog.util.serializer.Data2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author CubeMonkey
 * @create 2020-11-24 17:32
 */
@Data
public class ArticleDTO {
    /*文章编号*/
    private BigInteger articleId;

    /*文章标题*/
    private String articleTitle;

    /*文章内容*/
    private String articleContent;

    /*文章作者*/
    private String author;

    /*文章创建时间*/
    @JsonSerialize(using = Data2LongSerializer.class)
    private Date createTime;
}
