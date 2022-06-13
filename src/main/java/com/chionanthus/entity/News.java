package com.chionanthus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private int news_id;
    private String news_content;
    private Date news_create_time;
    private Date news_modified_time;
    private String news_title;
    private String news_describe;
    private int author_id;
    private int news_status;
    private int news_type;
}
