package com.myself.service.impl;

import com.myself.mapper.AdminMapper;
import com.myself.pojo.Admin;
import com.myself.pojo.AdminExample;
import com.myself.service.AdminService;
import com.myself.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：摘到星星了吗
 * @Date:2022/2/16 -02-16-10:43
 * @Description: com.myself.service.impl
 * @version:1.0
 */
@Service
public class AdminServiceImpl implements AdminService {

    //在逻辑业务层中，一定会有数据访问层的对象

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {

        //根据传入的用户或者到DB中查询相应用户对象
        //如果有条件，则一定要创建AdminExample的对象，用来封装条件

        AdminExample example = new AdminExample();

        //添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);

        if (list.size()>0){
            //得到账户
            Admin admin = list.get(0);

           /* 验证密文对比（先进行加密然后进行比对）
            数据库版本不支持加密，加密文本太长
            String mi = MD5Util.getMD5(pwd);
            */

            if (pwd.equals(admin.getaPass())){
                return admin;
            }

        }
        return null;
    }
}
