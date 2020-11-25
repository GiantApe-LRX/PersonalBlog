package com.cubemonkey.personalblog.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author CubeMonkey
 * @create 2020-11-24 17:53
 */
@Data
public class ArticleListForm {
    /*文章标题，用于模糊匹配*/
    private String articleTitle;

    /*文章创建时间最小值*/
    private Long createTimeMin;

    /*文章创建时间最大值*/
    private Long createTimeMax;

    /*文章作者*/
    private String author;

    /*文章分页的当前页数*/
    private Integer page = 0;

    /*文章分页的每页页数*/
    private Integer size = 10;
}
