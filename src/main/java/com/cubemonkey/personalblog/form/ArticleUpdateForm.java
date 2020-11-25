package com.cubemonkey.personalblog.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @author CubeMonkey
 * @create 2020-11-24 14:41
 */
@Data
public class ArticleUpdateForm {
    /*文章编号*/
    @NotNull(message = "articleId")
    BigInteger articleId;

    /*文章标题*/
    @NotEmpty(message = "articleTitle")
    String articleTitle;

    /*文章内容*/
    @NotEmpty(message = "articleContent")
    String articleContent;
}
