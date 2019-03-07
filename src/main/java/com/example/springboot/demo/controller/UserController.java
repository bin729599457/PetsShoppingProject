package com.example.springboot.demo.controller;

import com.example.springboot.demo.dao.UserMapper;
import com.example.springboot.demo.domain.AjaxJSON;
import com.example.springboot.demo.domain.TBUser;
import com.example.springboot.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
/**
 * @RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
 * RestController使用的效果是将方法返回的对象直接在浏览器上展示成json格式
 */
@RequestMapping(value = "/users")
public class UserController {

    //依赖注入
    @Autowired
    UserMapper userMapper;

//    @RequestMapping(value = "pic")
//    public long pic() {
//        //调用dao层
//        TBUser user = userMapper.selectUserByName();
//        return System.currentTimeMillis();
//    }


    /**
     * 注册用户
     * @param param
     */
    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    public AjaxJSON add_user(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();

        long id= System.currentTimeMillis();
        String account=param.get("account").toString();
        String password=param.get("passWord").toString();
        String userName=param.get("userName").toString();
        Integer isRoot= Integer.valueOf(param.get("isRoot").toString());
        String phone=param.get("phone").toString();
        String address=param.get("address").toString();

        if(account.isEmpty()||password.isEmpty()||userName.isEmpty()||phone.isEmpty()){
            ajaxJSON.setMsg("用户资料输入不全，注册失败");
            ajaxJSON.setSuccess(false);
            return ajaxJSON;
        }else {
            TBUser tbUser=new TBUser(id,account,password,userName,isRoot,phone,new Date(),address);
//            userService.addUser(tbUser);
            Map<String,Object> paramMap=new HashMap<>();
            paramMap.put("id",tbUser.getId());
            paramMap.put("account",tbUser.getAccount());
            paramMap.put("password",tbUser.getPassword());
            paramMap.put("userName",tbUser.getUserName());
            paramMap.put("isRoot",tbUser.getIsRoot());
            paramMap.put("phone",tbUser.getPhone());
            paramMap.put("createTime",tbUser.getCreateTime());
            if(!tbUser.getAddress().isEmpty()){
                paramMap.put("address",tbUser.getAddress());
            }else {
                paramMap.put("address","");
            }
            userMapper.addUser(paramMap);
            ajaxJSON.setMsg("用户添加成功");
        }

        return ajaxJSON;
    }

    /**
     * 用户登录
     * @param param
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public AjaxJSON login(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        String account=param.get("account").toString();
        String password=param.get("password").toString();

        if(account.isEmpty()||password.isEmpty()){
            ajaxJSON.setMsg("帐号密码不可为空");
            ajaxJSON.setSuccess(false);
        }else {

            Map<String,Object> paramMap=new HashMap<>();
            paramMap.put("account",account);
            paramMap.put("password",password);
            HashMap resultMap= (HashMap) userMapper.userLogin(paramMap);
            if(resultMap==null){
                ajaxJSON.setMsg("帐号、密码输入错误");
                ajaxJSON.setSuccess(false);
            }else {
                ajaxJSON.setMsg("登录成功");
            }
        }
        return ajaxJSON;
    }

    /**
     * 管理员获取所有用户
     */
    @RequestMapping(value = "getAllUsers",method = RequestMethod.GET)
    public AjaxJSON getAllUsers(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        List<TBUser> tbUserList=new ArrayList<>();
        tbUserList=userMapper.getAllUsers();
        ajaxJSON.setObj(tbUserList);
        ajaxJSON.setMsg("查询成功");

        return ajaxJSON;
    }
}