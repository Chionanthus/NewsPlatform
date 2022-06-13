create table news(
	news_content VARCHAR(300),
	 news_id INT(10) PRIMARY KEY AUTO_INCREMENT,
	 news_create_time TIMESTAMP ,
	 news_modified_time TIMESTAMP,
	 news_title VARCHAR(50), 
	 author_id int(10),
	 news_type int(10),
	 org_id int(10),
	 news_describe VARCHAR(100)
)

create table newstype(
	type_id int(10),
	type_name VARCHAR(20)
)

create table user(
	user_id INT(10) PRIMARY KEY AUTO_INCREMENT,
	user_name VARCHAR(20),
	user_password VARCHAR(20),
	user_create_time TIMESTAMP,
	user_email VARCHAR(30),
	user_modified_time TIMESTAMP,
	user_phone VARCHAR(15),
	org_id int(10),
	role int(10),
	user_address VARCHAR(40),
	user_describe VARCHAR(50),
	user_sex INT(3)
)

create TABLE comment(
	comment_id int(10) PRIMARY KEY AUTO_INCREMENT,
	comment_content VARCHAR(100),
	comment_create_time VARCHAR(40),
	comment_modified_time VARCHAR(40),
	news_id int(10),
	comment_author_id int(10),
	author_name VARCHAR(20),
	comment_status int(5)

)
