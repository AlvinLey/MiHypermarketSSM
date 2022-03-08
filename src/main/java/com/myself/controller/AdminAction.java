package com.myself.controller;

import com.myself.pojo.Admin;
import com.myself.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：摘到星星了吗
 * @Date:2022/2/16 -02-16-16:09
 * @Description: com.myself.controller
 * @version:1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {

    //所有的界面层，一定会有业务逻辑层的对象

    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public String login(String name,String pwd,HttpServletRequest request){
        Admin admin = adminService.login(name, pwd);
        if (admin != null){
            request.setAttribute("admin",admin);
            return "main";

        }else {
            request.setAttribute("errmsg","用户名或者密码不正确");
            return "login";
        }
    }

}
