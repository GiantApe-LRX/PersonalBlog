package com.cubemonkey.personalblog.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @author CubeMonkey
 * @create 2020-11-25 9:04
 */
@Data
public class CommentPublishForm {

    /*文章编号*/
    @NotNull(message = "articleId")
    private BigInteger articleId;

    /*评论内容*/
    @NotEmpty(message = "commentContent")
    private String commentContent;

    /*评论的目标编号*/
    @NotNull(message = "parentCommentId")
    private BigInteger parentCommentId;


}
