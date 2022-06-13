package com.chionanthus.controller;

import com.chionanthus.entity.Comment;
import com.chionanthus.entity.News;
import com.chionanthus.service.CommentService;
import com.chionanthus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @RequestMapping(value="/addComment",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public void addComment(String comment_content,String news_id,String comment_author_id,String user_role, HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");
        Comment comment =new Comment();
        comment.setNews_id(Integer.parseInt(news_id));
        comment.setComment_content(comment_content);//内容
        comment.setComment_author_id(Integer.parseInt(comment_author_id));
        comment.setAuthor_name(userService.queryUserNameByID(Integer.parseInt(comment_author_id)));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        comment.setComment_create_time(LocalDateTime.now().format(formatter));

        if(commentService.addComment(comment)==1)
        {
//            if("3".equals(user_role))
//            {
                commentService.PassComment(comment.getComment_id());
                //默认不审核了
//            }
//            else
//            {
//                commentService.SuspensionComment(comment.getComment_id());
//            }
            response.getWriter().print("评论成功");
        }
        else
        {
            response.getWriter().print("评论失败");
        }
    }


    @RequestMapping("/MyCommentList/{user_id}")
    public String MyCommentList(@PathVariable(name="user_id") int user_id, Model model)
    {
        List<Comment> list = commentService.queryCommentByAuthorId(user_id);
        model.addAttribute("comments",list);
        return "/user/MyCommentList";
    }

    @RequestMapping("/comment/queryCommentLimitByAuthor/{user_id}")
    public String queryCommentLimitByAuthor(@PathVariable(name="user_id") int user_id, @RequestParam("comment_content") String comment_content, Model model)
    {
        List<Comment> list = commentService.queryCommentLimitByAuthor(user_id,comment_content);
        model.addAttribute("comments",list);
        return "/user/MyCommentList";
    }
    @RequestMapping("/comment/deleteCommentInPage/{news_id}/{comment_id}")
    public String deleteComment(@PathVariable("news_id")int news_id,@PathVariable("comment_id") int comment_id)
    {
        commentService.deleteComment(comment_id);
        return "redirect:/viewnews/"+news_id;
    }
}
