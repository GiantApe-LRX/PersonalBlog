package com.cubemonkey.personalblog.exception;

import com.cubemonkey.personalblog.enums.ResultEnum;

/**
 * @author CubeMonkey
 * @create 2020-11-22 13:24
 */
public class BlogException extends RuntimeException {
    private Integer code;

    public BlogException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public BlogException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();

    }
}
