package com.example.springboot.demo.controller;

import com.example.springboot.demo.dao.UserMapper;
import com.example.springboot.demo.domain.TBUser;
import com.example.springboot.demo.domain.User;
import com.example.springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
/**
 * @RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
 * RestController使用的效果是将方法返回的对象直接在浏览器上展示成json格式
 */
public class UserController {

    //依赖注入
    @Autowired
    UserMapper userMapper;

    UserService userService;


    @RequestMapping(value = "pic")
    public long pic() {
        //调用dao层
        User user = userMapper.selectUserByName("wx");
        return System.currentTimeMillis();
    }


    @RequestMapping(value = "addUser")
    public void add_user(@RequestParam Map<String,Object> param){

        long id= System.currentTimeMillis();
        String account=param.get("account").toString();
        String password=param.get("passWord").toString();
        String userName=param.get("userName").toString();
        Integer isRoot= Integer.valueOf(param.get("isRoot").toString());
        String phone=param.get("phone").toString();
        String address=param.get("address").toString();

        TBUser tbUser=new TBUser(id,account,password,userName,isRoot,phone,new Date(),address);

//        String


    }

}