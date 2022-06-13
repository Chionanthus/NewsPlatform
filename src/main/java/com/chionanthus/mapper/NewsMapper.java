package com.chionanthus.mapper;

import com.chionanthus.entity.News;
import com.chionanthus.entity.NewsType;
import com.chionanthus.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapper {
    int addNews(News news);

    public List<NewsType> queryNewsType();

    News queryNewsByID(int news_id);

    List<News> queryAllNews();

    List<News> queryNewsByPageNum();

    int deleteNews(int news_id);

    int modifyNews(News news);

    List<News> queryNewsByTitle(String news_title);

    List<News> queryNewsByType(int i);

    String queryNewsTypeNameByID(int news_type);

    List<News> queryCensoredNewsByType(int news_type);

    int passNews(int news_id);

    int rejectNews(int news_id);

    int suspensionNews(int news_id);

    List<News> queryNewsByAuthorId(int author_id);


    List<News> queryNewsLimitByAuthor(@Param("user_id") int user_id,@Param("news_title") String news_title);

    List<News> queryCensoredNewsByAuthorId(Integer author_id);

    List<News> queryCensoredNewsByTitle(String queryNewsTitle);
}
