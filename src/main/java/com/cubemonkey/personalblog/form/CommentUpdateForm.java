package com.cubemonkey.personalblog.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @author CubeMonkey
 * @create 2020-11-25 9:31
 */
@Data
public class CommentUpdateForm {
    /*文章标题*/
    @NotNull(message = "commentId")
    private BigInteger commentId;

    /*评论内容*/
    @NotEmpty(message = "commentContent")
    private String commentContent;
}
