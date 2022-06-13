package com.chionanthus.controller;

import com.baidu.ueditor.ActionEnter;
import com.chionanthus.entity.Comment;
import com.chionanthus.entity.News;
import com.chionanthus.entity.NewsType;
import com.chionanthus.service.CommentService;
import com.chionanthus.service.NewsService;
import com.chionanthus.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Log4j
public class NewsController {
    @Autowired
    NewsService newsService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @RequestMapping("/tomain")
    public String toMain(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum)
    {
//        PageHelper.startPage(pageNum,3);
        //默认第一页，一页4个
        List<News> newsList1 = newsService.queryCensoredNewsByType(1);
        List<News> newsList2 = newsService.queryCensoredNewsByType(2);
        List<News> newsList3 = newsService.queryCensoredNewsByType(3);
        List<News> newsList4 = newsService.queryCensoredNewsByType(4);
        List<News> limitList1 = new ArrayList<>();
        List<News> limitList2= new ArrayList<>();;
        List<News> limitList3= new ArrayList<>();;
        List<News> limitList4= new ArrayList<>();;
        if(newsList1.size()<=3)limitList1=newsList1;
        else  for(int i=0;i<3;i++)limitList1.add(newsList1.get(i));

        if(newsList2.size()<=3)limitList2=newsList2;
        else  for(int i=0;i<3;i++)limitList2.add(newsList2.get(i));

        if(newsList3.size()<=3)limitList3=newsList3;
        else  for(int i=0;i<3;i++)limitList3.add(newsList3.get(i));

        if(newsList4.size()<=3)limitList4=newsList4;
        else  for(int i=0;i<3;i++)limitList4.add(newsList4.get(i));

        model.addAttribute("pageInfo1",limitList1);
        model.addAttribute("pageInfo2",limitList2);
        model.addAttribute("pageInfo3",limitList3);
        model.addAttribute("pageInfo4",limitList4);
        return "main.html";
    }
    @RequestMapping("/toNewsList/{news_Type}")
    public String toNewsList(@PathVariable(name="news_Type")Integer news_Type,Model model,
                             @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum)
    {
        PageHelper.startPage(pageNum,6);
        List<News> newsList = newsService.queryCensoredNewsByType(news_Type);
        String Type_name = newsService.queryNewsTypeNameByID(news_Type);
        PageInfo<News> pageInfo = new PageInfo<News>(newsList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("news_Type",news_Type);
        model.addAttribute("Type_name",Type_name);
        return "/news/listnews";
    }
    @RequestMapping("/news/queryCensoredNewsByTitle")
    public String queryCensoredNewsByTitle(@RequestParam("queryNewsTitle") String queryNewsTitle,Model model,
                                           @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum)
    {
        PageHelper.startPage(pageNum,6);
        List<News> newsList = newsService.queryCensoredNewsByTitle(queryNewsTitle);
        PageInfo<News> pageInfo = new PageInfo<News>(newsList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("Type_name","搜索结果");
        return "/news/listnews";
    }
    @RequestMapping("/toNewsListOfAuthor/{author_id}")
    public String toNewsListOfAuthor(@PathVariable(name="author_id")Integer author_id,Model model,
                             @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum)
    {
        PageHelper.startPage(pageNum,6);
        List<News> newsList = newsService.queryCensoredNewsByAuthorId(author_id);
        String author_name = userService.queryUserNameByID(author_id);
        PageInfo<News> pageInfo = new PageInfo<News>(newsList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("author_id",author_id);
        model.addAttribute("author_name",author_name);
        return "/news/listNewsOfAuthor";
    }

    @RequestMapping("/toAddNews")
    public String toAddNews()
    {
        return "/news/addnews";
    }

    @RequestMapping("/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        System.out.println(rootPath);
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @RequestMapping(value="/addNews",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public void addNews(String news_title,String author_id, String editor_text,String puretext,
                        String user_role, String news_type, HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");
        News news =new News();
//        String describe =puretext.substring(0,11)+"...";
        news.setNews_title(news_title);
        news.setNews_type(Integer.parseInt(news_type));
        news.setNews_content(editor_text);
        news.setNews_describe(puretext);
        news.setAuthor_id(Integer.parseInt(author_id));
        news.setNews_create_time(new Date());
        if(newsService.addNews(news)==1)
        {
            if("3".equals(user_role))
            {
                newsService.passNews(news.getNews_id());
                //管理员编写新闻默认设置为通过
            }
            else
            {
                newsService.suspensionNews(news.getNews_id());
                //其余人员编写新闻默认设置为未审核状态
            }
          response.getWriter().print("添加新闻成功");
        }
        else
        {
            response.getWriter().print("添加新闻失败");
        }
    }

    @RequestMapping("/viewnews/{news_id}")
    public String toViewNews(@PathVariable(name="news_id")String news_id, Model model)
    {
        News news = newsService.queryNewsByID(Integer.parseInt(news_id));
        List<Comment> commentList=commentService.queryCensoredCommentByNewsID(Integer.parseInt(news_id));
        for (Comment comment : commentList) {
            comment.setAuthor_name(userService.queryUserNameByID(comment.getComment_author_id()));
        }
        String news_type = newsService.queryNewsTypeNameByID(news.getNews_type());
        String author_name = userService.queryUserNameByID(news.getAuthor_id());
        model.addAttribute("commentList",commentList);
        model.addAttribute("news",news);
        model.addAttribute("news_type",news_type);
        model.addAttribute("author_name",author_name);

        return "news/viewnews";
    }



}
