package com.chionanthus.controller;


import com.chionanthus.entity.NewsType;
import com.chionanthus.entity.User;
import com.chionanthus.service.NewsService;
import com.chionanthus.service.UserService;
import com.mysql.cj.util.StringUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Log4j
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    @RequestMapping("/login")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        Map<String, Object> map, HttpSession session)
    {
        User user = new User();
        user.setUser_name(username);
        user.setUser_password(password);
        if(!StringUtils.isEmptyOrWhitespaceOnly(username)&&
            userService.queryUserByStrictName(username)==1
            &&userService.UserLogin(user).equals(password))
        {
            //登陆成功，这时需要更新user对象
            user = userService.queryOneUserInfoByName(username);
            List<NewsType> newsTypes = newsService.queryNewsType();
            session.setAttribute("newsTypes",newsTypes);
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(600);
            //登陆成功，session中添加用户后，跳到处理新闻分页的Controller
            return "redirect:/tomain";
//          return "main";
        }
        else
        {
            map.put("msg", "用户名密码错误");
            return "login";
        }
    }


    @RequestMapping("/dashboard")
    public String UserInfo()
    {
        return "user/dashboard";
    }

    @RequestMapping("/signup")
    public String signUp(@RequestParam("username")String username,
                         @RequestParam("password")String password,
                         Map<String, Object> map) {
        User user = new User();
        user.setUser_name(username);
        user.setUser_password(password);
        user.setUser_create_time(new Date());
        user.setRole(1);
        //用户名已经存在
        if(userService.queryUserByStrictName(username)==1)
        {
            map.put("msg", "用户名已经存在");
            return "signup";
        }
        else if(userService.addUser(user)==1)
        {
            map.put("msg", "注册成功");
            return "redirect:/login.html";
        }
        else
        {
            map.put("msg", "发生错误,无法注册");
            return "signup";
        }
    }
}
