package com.example.springboot.demo.controller;

import com.example.springboot.demo.dao.UserMapper;
import com.example.springboot.demo.domain.AjaxJSON;
import com.example.springboot.demo.domain.TBUser;
import com.example.springboot.demo.domain.User;
import com.example.springboot.demo.util.CurrPage;
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

    /**
     * 注册用户
     * @param param
     */
    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    public AjaxJSON add_user(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        try {
            String account=param.get("account").toString();
            String password=param.get("password").toString();
            String userName=param.get("userName").toString();
            String phone=param.get("phone").toString();

            if(account.isEmpty()||password.isEmpty()||userName.isEmpty()||phone.isEmpty()){
                ajaxJSON.setMsg("用户资料输入不全，注册失败");
                ajaxJSON.setSuccess(false);
                return ajaxJSON;
            }else {
                param.put("id",System.currentTimeMillis());
                param.put("createTime",new Date());
                userMapper.addUser(param);
                ajaxJSON.setMsg("用户添加成功");
            }
        }catch (Exception e){
            ajaxJSON.setSuccess(false);
            ajaxJSON.setMsg("重复的用户名"+e.getCause());
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
                ajaxJSON.setMsg("帐号、密码输入有误");
                ajaxJSON.setSuccess(false);
            }else {
                ajaxJSON.setMsg("登录成功");
            }
        }
        return ajaxJSON;
    }

    /**
     * 用户修改个人信息(包括修改密码)
     */
    @RequestMapping(value = "updateMsg",method = RequestMethod.PUT)
    public AjaxJSON updateMsg(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        try {
            userMapper.updateMsg(param);
            ajaxJSON.setMsg("修改成功");
        }catch (Exception e){
            ajaxJSON.setMsg("修改失败"+e.getCause());
            ajaxJSON.setSuccess(false);
        }

        return ajaxJSON;
    }

    /**
     * 管理员获取单个用户信息
     */
    @RequestMapping(value = "getSingleUsersInfo",method = RequestMethod.GET)
    public AjaxJSON getSingleUsersInfo(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        HashMap resultMap= (HashMap) userMapper.getSingleUsersInfo(param);
        if(resultMap==null){
            ajaxJSON.setMsg("没有此用户");
            ajaxJSON.setSuccess(false);

        }else {
            ajaxJSON.setObj(resultMap);
            ajaxJSON.setMsg("查询成功");
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

        //分页 需要分页功能添加pages:页数以及rows每页的size参数
        if(param.get("pages")!=null||param.get("rows")!=null){
            int pages= Integer.parseInt(param.get("pages").toString());
            int rows= Integer.parseInt(param.get("rows").toString());
            ajaxJSON.setObj(CurrPage.queryByPage(tbUserList,pages,rows));
        }else {
            ajaxJSON.setObj(tbUserList);
        }

        ajaxJSON.setMsg("查询成功");
        return ajaxJSON;
    }

    /**
     * 管理员删除用户
     */
    @RequestMapping(value = "delUsers",method = RequestMethod.DELETE)
    public AjaxJSON delUsers(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        userMapper.delUsers(param);
        ajaxJSON.setMsg("删除成功");

        return ajaxJSON;
    }

    /**
     * 用户找回密码
     */
    @RequestMapping(value = "forgetPassword",method = RequestMethod.GET)
    public AjaxJSON forgetPassword(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        String account=param.get("account").toString();
        String phone=param.get("phone").toString();
        if(account.isEmpty()||phone.isEmpty()){
            ajaxJSON.setMsg("输入不能为空");
            ajaxJSON.setSuccess(false);
        }

        HashMap<String,Object> resultMap= (HashMap<String, Object>) userMapper.forgetPassword(param);
        if(resultMap!=null){
            ajaxJSON.setObj(resultMap);
            ajaxJSON.setMsg("找回成功");
        }else {
            ajaxJSON.setMsg("帐号、手机输入错误，找回失败");
            ajaxJSON.setSuccess(false);
        }
        return ajaxJSON;
    }
}