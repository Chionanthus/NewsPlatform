package com.chionanthus.controller;


import com.chionanthus.entity.Comment;
import com.chionanthus.entity.News;
import com.chionanthus.entity.User;
import com.chionanthus.service.CommentService;
import com.chionanthus.service.NewsService;
import com.chionanthus.service.UserService;
import lombok.extern.log4j.Log4j;
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
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Controller
@Log4j
public class AdminController {

    @Autowired
    NewsService newsService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @RequestMapping("/allNews")
    public String allNews(Model model)
    {
        List<News> list = newsService.queryAllNews();
        model.addAttribute("list",list);
        return "/admin/allNews";
    }
    @RequestMapping("/allUser")
    public String allUser(Model model)
    {
        List<User> list = userService.queryUserList();
        model.addAttribute("user",list);
        return "/admin/allUser";
    }
    @RequestMapping("/allComment")
    public String allComment(Model model)
    {
        List<Comment> list = commentService.queryCommentList();
        for (Comment comment : list) {
            comment.setAuthor_name(userService.queryUserNameByID(comment.getComment_author_id()));
        }
        model.addAttribute("comments",list);
        return "/admin/allComment";
    }

    //点击管理新闻页面的编辑新闻按钮,把该news放到Model中，转移到editnews.html页面
    @RequestMapping("/news/toEditNews/{news_id}")
    public String toEditNews(@PathVariable(name="news_id") String news_id, Model model)
    {
        News news = newsService.queryNewsByID(Integer.parseInt(news_id));
        model.addAttribute("news",news);
        return "news/editnews";
    }

//     editnews.html页面的提交
    @RequestMapping(value="/editNews",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public void editNews(String news_title,  String editor_text, String news_type,
                         String news_id,String puretext, String  user_role,
                         HttpServletRequest request , HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");
        News news =new News();
        news.setNews_id(Integer.parseInt(news_id));
        news.setNews_title(news_title);
        news.setNews_describe(puretext);
        news.setNews_type(Integer.parseInt(news_type));
        news.setNews_content(editor_text);
        news.setNews_modified_time(new Date());
        if(newsService.modifyNews(news)==1)
        {
            if("3".equals(user_role))
            {
                newsService.passNews(Integer.parseInt(news_id));
                //管理员编辑新闻默认设置为通过
            }
            else
            {
                newsService.suspensionNews(Integer.parseInt(news_id));
                //其余人员编辑新闻默认设置为未审核状态
            }
            response.getWriter().print("编辑新闻成功");
        }
        else
        {
            response.getWriter().print("编辑新闻失败");
        }
    }
    //点击管理新闻页面的删除新闻按钮,转移到全部新闻页面
    @RequestMapping("/news/deleteNews/{news_id}")
    public String deleteNews(@PathVariable("news_id") int news_id)
    {
        newsService.deleteNews(news_id);
        return "redirect:/allNews";
    }
    @RequestMapping("/news/AuthorDeleteNews/{user_id}/{news_id}")
    public String AuthorDeleteNews(@PathVariable("news_id") int news_id,@PathVariable("user_id") int user_id)
    {
        newsService.deleteNews(news_id);
        return "redirect:/MyNewsList/"+user_id;
    }
    @RequestMapping("/news/queryNews")
    public String queryNews(@RequestParam("queryNewsTitle") String news_title, Model model)
    {
        List<News> list = newsService.queryNewsByTitle(news_title);
        model.addAttribute("list",list);
        return "/admin/allNews";
    }
    @RequestMapping("/comment/queryComment")
    public String queryComment(@RequestParam("queryCommentContent") String queryCommentContent, Model model)
    {
        List<Comment> list = commentService.adminQueryComment(queryCommentContent);
        model.addAttribute("comments",list);
        for (Comment comment : list) {
            comment.setAuthor_name(userService.queryUserNameByID(comment.getComment_author_id()));
        }
        return "/admin/allComment";
    }

    @RequestMapping("/user/deleteUser/{user_id}")
    public String deleteUser(@PathVariable("user_id") int user_id)
    {
        userService.deleteUser(user_id);
        return "redirect:/allUser";
    }


    @RequestMapping("/comment/deleteComment/{comment_id}")
    public String deleteComment(@PathVariable("comment_id") int comment_id)
    {
        commentService.deleteComment(comment_id);
        return "redirect:/allComment";
    }

    @RequestMapping("/news/queryUserByName")
    public String queryUserByName(@RequestParam("queryUserName") String queryUserName, Model model)
    {
        List<User> list = userService.queryUserByName(queryUserName);
        model.addAttribute("user",list);
        return "/admin/allUser";
    }

    @RequestMapping(value="/news/passNews/{news_id}")
    public String passNews(@PathVariable("news_id")String news_id,Model model) throws IOException {
        if(newsService.passNews(Integer.parseInt(news_id))==1)
        {
            model.addAttribute("msg","审核成功");
            model.addAttribute("style","#0B9443");
            return "forward:/allNews";
        }
        else
        {
            model.addAttribute("msg","审核错误");
            model.addAttribute("style","red");
            return "forward:/allNews";
        }
    }

    @RequestMapping("/news/rejectNews/{news_id}")
    public String rejectNews(@PathVariable("news_id")String news_id,Model model) throws IOException {

        if(newsService.rejectNews(Integer.parseInt(news_id))==1)
        {
            model.addAttribute("msg","驳回成功");
            model.addAttribute("style","#0B9443");
            return "forward:/allNews";
        }
        else
        {
            model.addAttribute("msg","驳回错误");
            model.addAttribute("style","red");
            return "forward:/allNews";
        }
    }
    //可以在viewnews那边改成AJAX，这里摆烂了
    @RequestMapping(value="/news/passNewsInPage/{news_id}")
    public String passNewsInPage(@PathVariable("news_id")String news_id,Model model) throws IOException {
        if(newsService.passNews(Integer.parseInt(news_id))==1)
        {

            return "redirect:/viewnews/"+news_id;
        }
        else
        {
            return "redirect:/viewnews/"+news_id;
        }
    }
    @RequestMapping("/news/rejectNewsInPage/{news_id}")
    public String rejectNewsInPage(@PathVariable("news_id")String news_id,Model model) throws IOException {

        if(newsService.rejectNews(Integer.parseInt(news_id))==1)
        {
            return "redirect:/viewnews/"+news_id;
        }
        else
        {
            return "redirect:/viewnews/"+news_id;
        }
    }

    @RequestMapping("/toAddAuthor")
    public String toAddAuthor()
    {
        return "admin/addAuthor";
    }

    @RequestMapping(value="/addAuthor",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public void addAuthor(String user_name,String user_password,HttpServletRequest request , HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");

        User user = new User();
        user.setUser_name(user_name);
        user.setUser_password(user_password);
        user.setUser_create_time(new Date());
        user.setRole(2);
        if(userService.queryUserByStrictName(user_name)==1)
        {
            response.getWriter().print("用户名已经存在");
        }
        else if(userService.addUser(user)==1)
        {
            response.getWriter().print("新增作者成功");
        }
        else
        {
            response.getWriter().print("发生错误");
        }
    }


    @RequestMapping(value="/admin/switchRole/{user_id}")
    public String switchRole(@PathVariable("user_id")String user_id,Model model) throws IOException {
        if(userService.switchRole(Integer.parseInt(user_id))==1)
        {
            model.addAttribute("msg","转换角色成功");
            model.addAttribute("style","#0B9443");
            return "redirect:/allUser";
        }
        else
        {
            model.addAttribute("msg","转换角色错误");
            model.addAttribute("style","red");
            return "forward:/allUser";
            //只有forward才能传输model，success直接省略了
        }
    }
}
