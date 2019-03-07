package com.example.springboot.demo.controller;


import com.example.springboot.demo.dao.PetsMapper;
import com.example.springboot.demo.domain.AjaxJSON;
import com.example.springboot.demo.domain.TBPets;
import com.example.springboot.demo.util.CurrPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pets")
public class PetsController {

    @Autowired
    PetsMapper petsMapper;

    /**
     * 获取所有的宠物信息
     * @param param
     * @return
     */
    @RequestMapping(value = "getAllPets",method = RequestMethod.GET)
    public AjaxJSON getAllPets(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        List<TBPets>tbPetsList=petsMapper.getAllPets();

        //分页 需要分页功能添加pages:页数以及rows每页的size参数
        if(param.get("pages")!=null||param.get("rows")!=null){
            int pages= Integer.parseInt(param.get("pages").toString());
            int rows= Integer.parseInt(param.get("rows").toString());
            ajaxJSON.setObj(CurrPage.queryByPage(tbPetsList,pages,rows));
        }else{
            ajaxJSON.setObj(tbPetsList);
        }

        ajaxJSON.setMsg("查询成功");
        return ajaxJSON;
    }

    /**
     * 获取单个宠物信息
     */
    @RequestMapping(value = "getSinglePetInfo",method = RequestMethod.GET)
    public AjaxJSON getSinglePetInfo(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        HashMap resultMap= (HashMap) petsMapper.getSinglePetInfo(param);
        if(resultMap==null){
            ajaxJSON.setMsg("该宠物不存在");
            ajaxJSON.setSuccess(false);

        }else {
            ajaxJSON.setObj(resultMap);
            ajaxJSON.setMsg("查询成功");
        }
        return ajaxJSON;
    }

    /**
     * 管理员添加宠物
     * @param param
     * @return
     */
    @RequestMapping(value = "addPets",method = RequestMethod.POST)
    public AjaxJSON addPets(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();

        try {
            param.put("imgPath", ResourceUtils.getFile("classpath:img").toString());
//            param.put("imgPath", "/Users/binbin/JavaProjects/springBootDemo/src/main/resources/img/zero01.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        param.put("id",System.currentTimeMillis());
        petsMapper.addPets(param);
        ajaxJSON.setMsg("添加成功");

        return ajaxJSON;
    }

    /**
     * 更新宠物信息
     */
    @RequestMapping(value = "updatePetMsg",method = RequestMethod.PUT)
    public AjaxJSON updatePetMsg(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        try {
            petsMapper.updatePetMsg(param);
            ajaxJSON.setMsg("修改成功");
        }catch (Exception e){
            ajaxJSON.setMsg("修改失败"+e.getCause());
            ajaxJSON.setSuccess(false);
        }

        return ajaxJSON;
    }

    /**
     * 管理员删除宠物信息
     */
    @RequestMapping(value = "delUsers",method = RequestMethod.DELETE)
    public AjaxJSON delUsers(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        petsMapper.delPet(param);
        ajaxJSON.setMsg("删除成功");

        return ajaxJSON;
    }

}
