package com.cubemonkey.personalblog.controller;

import com.cubemonkey.personalblog.vo.ResultVO;
import com.cubemonkey.personalblog.entity.User;
import com.cubemonkey.personalblog.enums.ResultEnum;
import com.cubemonkey.personalblog.form.UserForm;
import com.cubemonkey.personalblog.service.UserService;
import com.cubemonkey.personalblog.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;

/**
 * @author CubeMonkey
 * @create 2020-11-20 14:42
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResultVO register(@Valid UserForm userForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【用户注册】参数不正确，loginForm={}", userForm);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }

        if (userService.findUserByUsername(userForm.getUsername()) != null){
            log.error("【用户注册】用户名已存在，username={}", userForm.getUsername());
            return ResultVOUtil.error(ResultEnum.USER_EXISTS);
        }

        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());

        User result = userService.createUser(user);
        log.info("【用户注册】注册成功，username:{}", result.getUsername());
        return ResultVOUtil.success();
    }

    @PostMapping("/login")
    public ResultVO login(@Valid UserForm userForm, BindingResult bindingResult, HttpSession session){

        if (session.getAttribute("userId") != null){
            log.error("【用户登录】已存在用户，不能重复登录，请先登出");
            return ResultVOUtil.error(ResultEnum.HAS_LOGIN);
        }

        if (bindingResult.hasErrors()){
            log.error("【用户登录】参数不正确，loginForm={}", userForm);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }

        User user = userService.findUserByUsername(userForm.getUsername());

        if (user == null){
            log.error("【用户登录】用户名({})不存在", userForm.getUsername());
            return ResultVOUtil.error(ResultEnum.USER_NOT_FOUND);
        }
        if (!userService.checkLogin(user, userForm.getPassword())){
            log.error("【用户登录】登陆失败，用户名和密码不匹配", user.getUsername());
            return ResultVOUtil.error(ResultEnum.USER_OR_PASSWORD_ERROR);
        }
        log.info("【用户登录】登录成功，username={}", user.getUsername());
        session.setAttribute("userId", user.getUserId());
        return ResultVOUtil.success();
    }

    @GetMapping("/logout")
    public ResultVO logout(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            log.error("【用户注销】请先登录");
            return ResultVOUtil.error(ResultEnum.NONE_LOGIN);
        } else {
            BigInteger userId = (BigInteger) session.getAttribute("userId");
            session.removeAttribute("userId");
            log.info("【用户注销】当前用户:{} 已注销", userId);
            return ResultVOUtil.success();
        }
    }
}
