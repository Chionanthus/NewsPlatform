package com.chionanthus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int user_id;
    private String user_name;
    private String user_password;
    private Date user_create_time;
    private String user_email;
    private Date user_modified_time;
    private String user_phone;
    private int org_id;
    private int role;
    private String user_address;
    private String user_describe;
    private int user_sex;

}
