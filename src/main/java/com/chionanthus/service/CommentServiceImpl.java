package com.chionanthus.service;

import com.chionanthus.entity.Comment;
import com.chionanthus.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public int addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Override
    public int PassComment(int comment_id) {
        return commentMapper.PassComment(comment_id);
    }

    @Override
    public int RejectComment(int comment_id) {
        return commentMapper.RejectComment(comment_id);
    }

    @Override
    public int SuspensionComment(int comment_id) {
        return commentMapper.SuspensionComment(comment_id);
    }

    @Override
    public List<Comment> queryCensoredCommentByNewsID(int news_id) {
        return commentMapper.queryCensoredCommentByNewsID(news_id);
    }

    @Override
    public List<Comment> queryCommentList() {
        return commentMapper.queryCommentList();
    }

    @Override
    public List<Comment> adminQueryComment(String queryCommentContent) {
        return commentMapper.adminQueryComment(queryCommentContent);
    }

    @Override
    public int deleteComment(int comment_id) {
        return commentMapper.deleteComment(comment_id);
    }

    @Override
    public List<Comment> queryCommentByAuthorId(int user_id) {
        return commentMapper.queryCommentByAuthorId(user_id);
    }

    @Override
    public List<Comment> queryCommentLimitByAuthor(int user_id, String comment_content) {
        return commentMapper.queryCommentLimitByAuthor(user_id,comment_content);
    }
}
