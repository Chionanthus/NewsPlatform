package com.chionanthus.mapper;

import com.chionanthus.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    int addComment(Comment comment);
    int PassComment(int comment_id);
    int RejectComment(int comment_id);
    int SuspensionComment(int comment_id);
    List<Comment> queryCensoredCommentByNewsID(int news_id);

    List<Comment> queryCommentList();

    List<Comment> adminQueryComment(String queryCommentContent);

    int deleteComment(int comment_id);

    List<Comment> queryCommentByAuthorId(int user_id);

    List<Comment> queryCommentLimitByAuthor(@Param("user_id") int user_id,@Param("comment_content") String comment_content);
}
