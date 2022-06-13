package com.chionanthus.controller;

import com.chionanthus.entity.User;
import com.chionanthus.mapper.UserMapper;
import com.chionanthus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/toUpdateUser/{user_id}")
    public String toUpdateUser(@PathVariable(name="user_id")Integer user_id, Model model)
    {
        User user=userService.queryUserById(user_id);
        if(user.getUser_describe()==null)user.setUser_describe("");
        model.addAttribute("UpdateUser",user);
        return "/user/updateUser";
    }

    @RequestMapping("/toAlterPassWord/{user_id}")
    public String toAlterPassWord(@PathVariable(name="user_id")Integer user_id, Model model)
    {
        User user=userService.queryUserById(user_id);
        model.addAttribute("UpdateUser",user);
        return "/user/alterPassword";
    }
    @RequestMapping(value="/UpdateUser",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public void UpdateUser(String user_id,String user_name,String user_email,String user_phone,
                            String user_address,String user_describe,
                            HttpServletRequest request,HttpServletResponse response)throws IOException
    {
        request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");
        User user= new User();
        user.setUser_id(Integer.parseInt(user_id));
        user.setUser_name(user_name);
        user.setUser_email(user_email);
        user.setUser_phone(user_phone);
        user.setUser_address(user_address);
        user.setUser_describe(user_describe);
        user.setUser_modified_time(new Date());
        //如果当前输入的用户名没改变
        if(userService.queryUserNameByID(Integer.parseInt(user_id)).equals(user_name))
        {
            if(userService.updateUser(user)==1)
            {
            response.getWriter().print("修改用户信息成功");
            }
            else
            {
            response.getWriter().print("修改用户信息失败");
            }
        }
        //尝试修改用户名
        else
        {
            if(userService.queryUserByStrictName(user_name)==1)
            {
                response.getWriter().print("该用户名已被占用");
            }
            else if(userService.updateUser(user)==1)
            {
                response.getWriter().print("修改用户信息成功");
            }
            else
            {
                response.getWriter().print("修改用户信息失败");
            }
        }


    }
    @RequestMapping(value="/alterPassword",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public void alterPassword(String user_id,String user_name,String user_password,
                           HttpServletRequest request,HttpServletResponse response)throws IOException
    {
        request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");
        User user= new User();
        user.setUser_id(Integer.parseInt(user_id));
        user.setUser_name(user_name);
        user.setUser_password(user_password);
        user.setUser_modified_time(new Date());
        if(userService.alterPassword(user)==1)
        {
            response.getWriter().print("修改密码成功");
        }
        else
        {
            response.getWriter().print("修改密码失败");
        }
    }
    @RequestMapping("/signout")
    public String toUpdateUser(HttpSession session)
    {
        session.invalidate();
        return "redirect:/login";
    }

}
