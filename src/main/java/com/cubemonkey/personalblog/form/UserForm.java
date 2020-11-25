package com.cubemonkey.personalblog.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author CubeMonkey
 * @create 2020-11-20 15:03
 */
@Data
public class UserForm {
    /*用户名*/
    @NotEmpty(message = "username")
    private String username;

    /*密码*/
    @NotEmpty(message = "password")
    private String password;

}
