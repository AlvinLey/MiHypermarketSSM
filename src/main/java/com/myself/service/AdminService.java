package com.myself.service;

import com.myself.pojo.Admin;

/**
 * @Author：摘到星星了吗
 * @Date:2022/2/16 -02-16-10:41
 * @Description: com.myself.service
 * @version:1.0
 */
public interface AdminService {
    /**
     * @param name 登录用户名
     * @param pwd 登录密码
     * @return 允许的登录账户
     */
    Admin login(String name,String pwd);
}
