package com.example.springboot.demo.controller;

import com.example.springboot.demo.dao.UserMapper;
import com.example.springboot.demo.domain.AjaxJSON;
import com.example.springboot.demo.domain.TBUser;
import com.example.springboot.demo.domain.User;
import com.example.springboot.demo.util.CurrPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false, paramType = "body"),
            @ApiImplicitParam(name = "account", value = "帐号", dataType = "varchar", required = true, paramType = "body"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "varchar", required = true, paramType = "body"),
            @ApiImplicitParam(name = "userName", value = "用户名称", dataType = "varchar", required = true, paramType = "body"),
            @ApiImplicitParam(name = "isRoot", value = "用户权限（0：普通，1：管理员）", dataType = "int", required = true, paramType = "body"),
            @ApiImplicitParam(name = "phone", value = "手机", dataType = "varchar", required = true, paramType = "body"),
            @ApiImplicitParam(name = "address", value = "地址", dataType = "varchar", required = false, paramType = "body")
    })
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
    @ApiOperation(value = "用户登录" ,notes = "account:帐号;password:密码")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "account", value = "帐号", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "varchar", required = true)
    })
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public AjaxJSON login(@RequestParam Map<String,Object> param,HttpServletRequest httpServletRequest){

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
            TBUser resultMap=  userMapper.userLogin(paramMap);
            ajaxJSON.setObj(resultMap);
            if(resultMap==null){
                ajaxJSON.setMsg("帐号、密码输入有误");
                ajaxJSON.setSuccess(false);
            }else {
//                httpServletRequest.getSession().setAttribute("operator",resultMap.getAccount());
                ajaxJSON.setMsg("登录成功");
            }
        }
        return ajaxJSON;
    }

    @ApiOperation(value = "注销登录")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false)
    })
    @RequestMapping(value = "logOut",method = RequestMethod.POST)
    public AjaxJSON logOut(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();

        return ajaxJSON;
    }

    /**
     * 用户修改个人信息(包括修改密码)
     */
    @ApiOperation(value = "用户信息修改",notes = "password:密码;userName:用户名称;phone:手机;address:地址")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "id", value = "需要修改的用户ID", dataType = "varchar", required = true,paramType = "JSON"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "varchar", required = false, paramType = "JSON"),
            @ApiImplicitParam(name = "userName", value = "用户名称", dataType = "varchar", required = false, paramType = "body"),
            @ApiImplicitParam(name = "phone", value = "手机", dataType = "varchar", required = false, paramType = "body"),
            @ApiImplicitParam(name = "address", value = "地址", dataType = "varchar", required = false, paramType = "body")
    })
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
    @ApiOperation(value = "获取单个用户信息",notes = "id：用户ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param",required = false, paramType = "body"),
            @ApiImplicitParam(name = "id",value = "用户ID",dataType = "varchar",required = true,paramType ="path")
    })
    @RequestMapping(value = "getSingleUsersInfo",method = RequestMethod.POST)
    public AjaxJSON getSingleUsersInfo(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        TBUser resultMap=  userMapper.getSingleUsersInfo(param);
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
    @ApiOperation(value = "获取所有用户信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false, paramType = "body"),
            @ApiImplicitParam(name = "pages", value = "页码", dataType = "int", required = false, paramType = "path"),
            @ApiImplicitParam(name = "rows", value = "页大小", dataType = "int", required = false, paramType = "path")
    })
    @RequestMapping(value = "getAllUsers",method = RequestMethod.POST)
    public AjaxJSON getAllUsers(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        List<TBUser> tbUserList=new ArrayList<>();
        if(param.get("pages")!=null||param.get("rows")!=null){
            int pages= Integer.parseInt(param.get("pages").toString());
            int rows= Integer.parseInt(param.get("rows").toString());
            param.put("firstResult",(pages-1)*rows);
            param.put("maxResults",rows);
        }

        tbUserList=userMapper.getAllUsers(param);
        int total= Integer.parseInt(userMapper.getTotal().get("total").toString());

        ajaxJSON.setObj(tbUserList);
        ajaxJSON.setTotal(total);
        ajaxJSON.setMsg("查询成功");
        return ajaxJSON;
    }

    /**
     * 管理员删除用户
     */
    @ApiOperation(value = "删除用户",notes = "id:用户ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "id",value = "用户ID",dataType = "varchar",required = true)
    })
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
    @ApiOperation(value = "用户找回密码",notes = "account:帐号；phone:手机")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false, paramType = "body"),
            @ApiImplicitParam(name = "account", value = "帐号", dataType = "varchar", required = true, paramType = "path"),
            @ApiImplicitParam(name = "phone", value = "手机号码", dataType = "varchar", required = true, paramType = "path")
    })@RequestMapping(value = "forgetPassword",method = RequestMethod.POST)
    public AjaxJSON forgetPassword(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        String account=param.get("account").toString();
        String phone=param.get("phone").toString();
        if(account.isEmpty()||phone.isEmpty()){
            ajaxJSON.setMsg("输入不能为空");
            ajaxJSON.setSuccess(false);
        }

        TBUser resultMap= userMapper.forgetPassword(param);
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