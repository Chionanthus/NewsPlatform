package com.chionanthus.service;

import com.chionanthus.entity.News;
import com.chionanthus.entity.NewsType;
import com.chionanthus.entity.User;

import java.util.List;

public interface NewsService {

    int addNews(News news);

    List<NewsType> queryNewsType();

    News queryNewsByID(int parseInt);

    List<News> queryAllNews();

    List<News> queryNewsByPageNum();

    int deleteNews(int news_id);

    int modifyNews(News news);

    List<News> queryNewsByTitle(String news_title);
    //返回某一类型全部新闻的列表
    List<News> queryNewsByType(int i);
    //返回返回某一类型已审核通过新闻的列表
    List<News> queryCensoredNewsByType(int news_type);

    String queryNewsTypeNameByID(int news_type);


    int passNews(int news_id);

    int rejectNews(int news_id);

    int suspensionNews(int news_id);

    List<News> queryNewsByAuthorId(int user_id);

    List<News> queryNewsLimitByAuthor(int user_id,String news_title);

    List<News> queryCensoredNewsByAuthorId(Integer author_id);

    List<News> queryCensoredNewsByTitle(String queryNewsTitle);
}
