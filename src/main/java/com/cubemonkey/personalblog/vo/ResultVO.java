package com.cubemonkey.personalblog.vo;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @author CubeMonkey
 * @create 2020-11-20 14:46
 */
@Data
public class ResultVO<T> {
    /*错误码*/
    private Integer code;

    /*提示信息*/
    private String msg;

    /*具体内容*/
    private T data;
}
