package com.chionanthus.service;

import com.chionanthus.entity.News;
import com.chionanthus.entity.NewsType;
import com.chionanthus.entity.User;
import com.chionanthus.mapper.NewsMapper;
import com.chionanthus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService{


    @Autowired
    NewsMapper newsMapper;

    @Override
    public List<News> queryNewsByTitle(String news_title) {
        return newsMapper.queryNewsByTitle(news_title);
    }

    @Override
    public int modifyNews(News news) {
        return newsMapper.modifyNews(news);
    }
    @Override
    public List<News> queryAllNews() {
        return newsMapper.queryAllNews();
    }

    @Override
    public List<News> queryNewsByType(int i) {
        return newsMapper.queryNewsByType(i);
    }

    @Override
    public List<News> queryCensoredNewsByType(int news_type) {
        return newsMapper.queryCensoredNewsByType(news_type);
    }

    @Override
    public int passNews(int news_id) {
        return newsMapper.passNews(news_id);
    }

    @Override
    public int rejectNews(int news_id) {
        return  newsMapper.rejectNews(news_id);
    }

    @Override
    public int suspensionNews(int news_id) {
        return  newsMapper.suspensionNews(news_id);
    }

    @Override
    public String queryNewsTypeNameByID(int news_type) {
        return newsMapper.queryNewsTypeNameByID(news_type);
    }

    @Override
    public List<News> queryNewsByAuthorId(int user_id) {
        return newsMapper.queryNewsByAuthorId(user_id);
    }

    @Override
    public List<News> queryNewsByPageNum() {
        return newsMapper.queryNewsByPageNum();
    }

    @Override
    public int addNews(News news) {
        return newsMapper.addNews(news);
    }

    @Override
    public List<NewsType> queryNewsType() {
        return newsMapper.queryNewsType();
    }

    @Override
    public News queryNewsByID(int news_id) {
        return newsMapper.queryNewsByID(news_id);
    }

    @Override
    public int deleteNews(int news_id) {
        return newsMapper.deleteNews(news_id);
    }

    @Override
    public List<News> queryNewsLimitByAuthor(int user_id, String news_title) {
        return newsMapper.queryNewsLimitByAuthor(user_id,news_title);
    }

    @Override
    public List<News> queryCensoredNewsByAuthorId(Integer author_id) {
        return newsMapper.queryCensoredNewsByAuthorId(author_id);
    }

    @Override
    public List<News> queryCensoredNewsByTitle(String queryNewsTitle) {
        return newsMapper.queryCensoredNewsByTitle(queryNewsTitle);

    }
}
