package com.chionanthus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int comment_id;
    private String comment_content;
    private String comment_create_time;
    private String comment_modified_time;
    private int news_id;
    private int comment_author_id;
    private String author_name;
    private int comment_status;


}
