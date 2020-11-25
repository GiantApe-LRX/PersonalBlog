package com.cubemonkey.personalblog.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author CubeMonkey
 * @create 2020-11-24 14:12
 */
@Data
public class ArticlePublishForm {
    /*文章标题*/
    @NotEmpty(message = "articleTitle")
    private String articleTitle;
    /*文章内容*/
    @NotEmpty(message = "articleContent")
    private String articleContent;
}
