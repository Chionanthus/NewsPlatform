package com.chionanthus.service;

import com.chionanthus.entity.Comment;

import java.util.List;

public interface CommentService {

    int addComment(Comment comment);

    int PassComment(int comment_id);
    int RejectComment(int comment_id);
    int SuspensionComment(int comment_id);
    List<Comment> queryCensoredCommentByNewsID(int news_id);

    List<Comment> queryCommentList();

    List<Comment> adminQueryComment(String queryCommentContent);

    int deleteComment(int comment_id);

    List<Comment> queryCommentByAuthorId(int user_id);

    List<Comment> queryCommentLimitByAuthor(int user_id, String comment_content);
}
