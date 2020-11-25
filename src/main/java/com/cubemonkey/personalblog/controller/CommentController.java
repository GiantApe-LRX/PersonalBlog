package com.cubemonkey.personalblog.controller;

import com.cubemonkey.personalblog.entity.Comment;
import com.cubemonkey.personalblog.entity.User;
import com.cubemonkey.personalblog.enums.ResultEnum;
import com.cubemonkey.personalblog.form.CommentPublishForm;
import com.cubemonkey.personalblog.form.CommentUpdateForm;
import com.cubemonkey.personalblog.service.ArticleService;
import com.cubemonkey.personalblog.service.CommentService;
import com.cubemonkey.personalblog.service.UserService;
import com.cubemonkey.personalblog.util.ResultVOUtil;
import com.cubemonkey.personalblog.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;

/**
 * @author CubeMonkey
 * @create 2020-11-25 9:01
 */
@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;


    /**
     * 发表评论
     * @param commentPublishForm 表单提交对象
     * @param bindingResult 校验参数
     * @param session session对象
     * @return 处理返回结果
     */
    @PostMapping("/publish")
    public ResultVO publish(@Valid CommentPublishForm commentPublishForm,
                            BindingResult bindingResult,
                            HttpSession session){
        //参数检查
        if (bindingResult.hasErrors()){
            log.error("【评论发布】参数有误，commentPublishForm:{}", commentPublishForm);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        BigInteger userId = (BigInteger) session.getAttribute("userId");
        BigInteger articleId = commentPublishForm.getArticleId();
        //文章编号检查
        if (articleService.findByArticleId(articleId) == null){
            log.error("【评论发布】指定文章不存在，articleId={}", articleId);
            return ResultVOUtil.error((ResultEnum.ARTICLE_NOT_FOUND));
        }
        //指定评论编号检查
        BigInteger parentCommentId = commentPublishForm.getParentCommentId();
        if (!new BigInteger("0").equals(parentCommentId) && commentService.findByCommentId(parentCommentId) == null){
            log.error("【评论发布】指定评论不存在，parentCommentId={}", commentPublishForm.getParentCommentId());
            return ResultVOUtil.error(ResultEnum.COMMENT_NOT_FOUND);
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentPublishForm, comment);
        comment.setUserId(userId);
        commentService.publish(comment);
        return ResultVOUtil.success();
    }


    /**
     * 更新评论
     * @param commentUpdateForm 表单对象
     * @param bindingResult 校验参数
     * @param session session对象
     * @return 返回处理结果
     */
    @PostMapping("/update")
    public ResultVO update(@Valid CommentUpdateForm commentUpdateForm,
                           BindingResult bindingResult,
                           HttpSession session){
        //参数检查
        if (bindingResult.hasErrors()){
            log.error("【评论更新】参数有误，commentUpdateForm={}", commentUpdateForm);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }

        BigInteger userId = (BigInteger) session.getAttribute("userId");
        User user = userService.findUserById(userId);
        Comment comment = commentService.findByCommentId(commentUpdateForm.getCommentId());
        //指定评论编号检查
        if (comment == null){
            log.error("【评论更新】指定评论不存在，commentId={}",commentUpdateForm.getCommentId());
            return ResultVOUtil.error(ResultEnum.COMMENT_NOT_FOUND);
        }
        //权限检查
        if (!comment.getUserId().equals(userId) && !user.getType().equals(1)){
            log.error("【评论更新】权限不足，只有当前用户或管理员可以执行此操作");
            return ResultVOUtil.error(ResultEnum.NOT_ENOUGH_PERMISSIONS);
        }

        //评论修改
        BeanUtils.copyProperties(commentUpdateForm, comment);
        commentService.update(comment);
        return ResultVOUtil.success();
    }

    /**
     * 删除评论
     * @param commentId 评论编号
     * @param session session对象
     * @return 返回处理结果
     */
    @GetMapping("/delete")
    public ResultVO delete(@Param("commentId")BigInteger commentId, HttpSession session){
        //参数检查
        if(commentId == null){
            log.error("【评论删除】参数有误，commentId={}", commentId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }

        BigInteger userId = (BigInteger) session.getAttribute("userId");
        User user = userService.findUserById(userId);
        Comment comment = commentService.findByCommentId(commentId);
        //指定评论编号检查
        if (comment == null){
            log.error("【评论删除】指定评论不存在，commentId={}",comment);
            return ResultVOUtil.error(ResultEnum.COMMENT_NOT_FOUND);
        }
        //权限检查
        if (!comment.getUserId().equals(userId) && !user.getType().equals(1)){
            log.error("【评论删除】权限不足，只有当前用户或管理员可以执行此操作");
            return ResultVOUtil.error(ResultEnum.NOT_ENOUGH_PERMISSIONS);
        }

        //删除评论
        commentService.delete(commentId);
        return ResultVOUtil.success();
    }
}