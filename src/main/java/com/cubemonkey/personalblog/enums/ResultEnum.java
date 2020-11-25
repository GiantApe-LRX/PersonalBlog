package com.cubemonkey.personalblog.enums;

import lombok.Getter;

/**
 * @author CubeMonkey
 * @create 2020-11-22 13:31
 */
@Getter
public enum ResultEnum {
    PARAM_ERROR(1, "参数不正确"),

    USER_NOT_FOUND(2, "用户不存在"),

    USER_OR_PASSWORD_ERROR(3, "用户名或密码有误"),

    NONE_LOGIN(4, "请先登录"),

    HAS_LOGIN(5, "已存在用户，请先登出"),

    USER_EXISTS(6, "用户已存在"),

    ARTICLE_TITLE_AND_CONTENT_NOT_NULL(7, "文章标题与内容不能为空"),

    ARTICLE_NOT_FOUND(8, "未找到指定文章"),

    AUTHOR_NOT_FOUND(9, "未找到该文章作者"),

    NOT_ENOUGH_PERMISSIONS(10, "没有足够的权限，只有当前用户或管理员可以执行此操作"),

    COMMENT_NOT_FOUND(11, "未找到指定评论"),

    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
