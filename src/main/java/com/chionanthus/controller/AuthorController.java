package com.chionanthus.controller;

import com.chionanthus.entity.News;
import com.chionanthus.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    NewsService newsService;

    @RequestMapping("/MyNewsList/{user_id}")
    public String MyNewsList(@PathVariable(name="user_id") int user_id, Model model)
    {
        List<News> list = newsService.queryNewsByAuthorId(user_id);
        model.addAttribute("list",list);
        return "/author/MyNewsList";
    }

    @RequestMapping("/news/queryNewsLimitByAuthor/{user_id}")
    public String queryNewsLimitByAuthor(@PathVariable(name="user_id") int user_id, @RequestParam("queryNewsTitle") String news_title, Model model)
    {
        List<News> list = newsService.queryNewsLimitByAuthor(user_id,news_title);
        model.addAttribute("list",list);
        return "/author/MyNewsList";
    }
}
