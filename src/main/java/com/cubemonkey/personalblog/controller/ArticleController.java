package com.cubemonkey.personalblog.controller;

import com.cubemonkey.personalblog.service.CommentService;
import com.cubemonkey.personalblog.vo.ResultVO;
import com.cubemonkey.personalblog.dto.ArticleDTO;
import com.cubemonkey.personalblog.dto.ArticleDetailDTO;
import com.cubemonkey.personalblog.entity.Article;
import com.cubemonkey.personalblog.entity.Comment;
import com.cubemonkey.personalblog.entity.User;
import com.cubemonkey.personalblog.enums.ResultEnum;
import com.cubemonkey.personalblog.form.ArticleListForm;
import com.cubemonkey.personalblog.form.ArticlePublishForm;
import com.cubemonkey.personalblog.form.ArticleUpdateForm;
import com.cubemonkey.personalblog.service.ArticleService;
import com.cubemonkey.personalblog.service.UserService;
import com.cubemonkey.personalblog.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author CubeMonkey
 * @create 2020-11-24 14:01
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    /**
     * 发布文章
     * @param articlePublishForm 表单提交对象
     * @param bindingResult 校验参数
     * @param session 当前session对象
     * @return 处理结果
     */
    @PostMapping("/publish")
    public ResultVO publish(@Valid ArticlePublishForm articlePublishForm, BindingResult bindingResult, HttpSession session){
        //参数检查
        if (bindingResult.hasErrors()){
            log.error("【文章发布】参数有误，articlePublishForm={}", articlePublishForm);
            return ResultVOUtil.error(ResultEnum.ARTICLE_TITLE_AND_CONTENT_NOT_NULL);
        }
        //发布文章
        BigInteger userId = (BigInteger) session.getAttribute("userId");
        Article article = new Article();
        BeanUtils.copyProperties(articlePublishForm, article);
        article.setUserId(userId);
        articleService.publish(article);
        return ResultVOUtil.success();
    }

    /**
     * 更新文章信息
     * @param articleUpdateForm 表单提交对象
     * @param bindingResult 校验参数
     * @param session session对象
     * @return 处理结果
     */
    @PostMapping("/update")
    public ResultVO update(@Valid ArticleUpdateForm articleUpdateForm, BindingResult bindingResult, HttpSession session){
        //参数检查
        if (bindingResult.hasErrors()){
            log.error("【文章更新】参数有误，articleUpdateForm={}", articleUpdateForm);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        BigInteger userId = (BigInteger) session.getAttribute("userId");
        User user = userService.findUserById(userId);
        //文章编号检查
        Article article = articleService.findByArticleId(articleUpdateForm.getArticleId());
        if (article == null){
            log.error("【文章更新】找不到指定编号的文章，articleId={}", articleUpdateForm.getArticleId());
            return ResultVOUtil.error(ResultEnum.ARTICLE_NOT_FOUND);
        }

        //权限检查
        BigInteger authorId = article.getUserId();
        if (!userId.equals(authorId) && !user.getType().equals(1)){
            log.error("【文章更新】权限不足，只有文章作者或管理员可以执行此操作");
            return ResultVOUtil.error(ResultEnum.NOT_ENOUGH_PERMISSIONS);
        }

        //修改文章
        BeanUtils.copyProperties(articleUpdateForm, article);
        articleService.update(article);
        return ResultVOUtil.success();
    }

    /**
     * 删除文章
     * @param articleId 待删除的文章id
     * @param session session对象
     * @return 返回处理结果
     */
    @GetMapping("/delete")
    public ResultVO delete(@Param("articleId") BigInteger articleId, HttpSession session){
        //参数检查
        if (articleId == null){
            log.error("【文章删除】参数有误，articleId={}", articleId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        BigInteger userId = (BigInteger) session.getAttribute("userId");
        User user = userService.findUserById(userId);
        Article article = articleService.findByArticleId(articleId);
        //文章编号检查
        if (article == null){
            log.error("【文章删除】找不到指定id的文章，articleId={}", articleId);
            return ResultVOUtil.error(ResultEnum.ARTICLE_NOT_FOUND);
        }
        BigInteger authorId = article.getUserId();
        //权限检查
        if (!userId.equals(authorId) && !user.getType().equals(1)){
            log.error("【文章删除】权限不足，只有当前用户或管理员可以执行此操作");
            return ResultVOUtil.error(ResultEnum.NOT_ENOUGH_PERMISSIONS);
        }
        //删除文章
        articleService.delete(articleId);
        return ResultVOUtil.success();
    }

    /**
     * 多条件分页查询
     * @param articleListForm 表单提交对象
     * @param bindingResult 参数校验
     * @return 查询结果
     */
    @PostMapping("/list")
    public ResultVO<List<ArticleDTO>> list(@Valid ArticleListForm articleListForm, BindingResult bindingResult){
        //参数检查
        if (bindingResult.hasErrors()){
            log.error("【多条件分页查询】查询参数有误，articleListForm=",articleListForm);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        /**
         * 判断author是否为空，为空则authorId = null,
         * 不为空判断是否在数据库中存在，
         * 存在authorId即author.getUserId，不存在直接返回空数组
         */
        User author = null;
        BigInteger authorId = null;
        if (articleListForm.getAuthor() != null){
            author = userService.findUserByUsername(articleListForm.getAuthor());
            if (author == null){
                return ResultVOUtil.success(new ArrayList<Article>());
            }else{
                authorId = author.getUserId();
            }
        }

        //时间戳-> yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long createTimeMax = articleListForm.getCreateTimeMax();
        Long createTimeMin = articleListForm.getCreateTimeMin();
        String createTimeMinStr = createTimeMin == null ? null : sdf.format(new Date(createTimeMin));
        String createTimeMaxStr = createTimeMax == null ? null : sdf.format(new Date(createTimeMax));
        //获取分页对象
        PageRequest request = PageRequest.of(articleListForm.getPage(), articleListForm.getSize());
        //查询
        Page<Article> articlePage = articleService.findByRequire(articleListForm.getArticleTitle(),
                createTimeMinStr,
                createTimeMaxStr,
                authorId,
                request);
        //将Page<Article>中的数据转化为ArticleDTO
        List<Article> articleList = articlePage.getContent();
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setAuthor(userService.findUserById(article.getUserId()).getUsername());
            //若文章内容超过10，截取前十并在最后追加省略号
            if (articleDTO.getArticleContent().length() >= 10){
                articleDTO.setArticleContent(article.getArticleContent().substring(0, 10)+"......");
            }
            articleDTOList.add(articleDTO);
        }
        return ResultVOUtil.success(articleDTOList);
    }


    /**
     * 单篇文章查询
     * @param articleId 待查询的文章id
     * @return 文章信息
     */
    @GetMapping("/detail")
    public ResultVO<ArticleDetailDTO> detail(@Param("articleId")BigInteger articleId) {
        //参数检查
        if (articleId == null) {
            log.error("【多条件分页查询】查询参数有误，articleId={}", articleId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        ArticleDetailDTO articleDetailDTO = null;
        Article article = articleService.findByArticleId(articleId);
        if (article != null) {
            articleDetailDTO = new ArticleDetailDTO();
            BeanUtils.copyProperties(article, articleDetailDTO);
            User author = userService.findUserById(article.getUserId());
            articleDetailDTO.setAuthor(author.getUsername());
            articleDetailDTO.setCreateTime(article.getCreateTime().getTime());
            articleDetailDTO.setUpdateTime(article.getUpdateTime().getTime());
            List<Comment> comments = commentService.findByArticleId(articleId);
            articleDetailDTO.setComments(comments);
        }
        return ResultVOUtil.success(articleDetailDTO);
    }
}
