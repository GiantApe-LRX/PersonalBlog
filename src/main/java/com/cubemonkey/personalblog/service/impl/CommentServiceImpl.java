package com.cubemonkey.personalblog.service.impl;

import com.cubemonkey.personalblog.dao.CommentDAO;
import com.cubemonkey.personalblog.entity.Comment;
import com.cubemonkey.personalblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * @author CubeMonkey
 * @create 2020-11-24 23:06
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDAO commentDAO;

    @Override
    public List<Comment> findByArticleId(BigInteger articleId) {
        return commentDAO.findByArticleId(articleId);
    }

    @Override
    public Comment findByCommentId(BigInteger commentId) {
        return commentDAO.findByCommentId(commentId);
    }

    @Override
    public void publish(Comment comment) {
        commentDAO.save(comment);
    }

    @Override
    public void update(Comment comment) {
        commentDAO.save(comment);
    }

    @Override
    @Transactional
    public void delete(BigInteger commentId) {
        List<Comment> comments = commentDAO.findByParentCommentId(commentId);
        for (Comment comment : comments) {
            delete(comment.getCommentId());
        }
        commentDAO.deleteCommentByCommentId(commentId);
    }
}
