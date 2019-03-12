package com.example.springboot.demo.controller;


import com.example.springboot.demo.dao.PetsMapper;
import com.example.springboot.demo.domain.AjaxJSON;
import com.example.springboot.demo.domain.TBPets;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "getAllPets",method = RequestMethod.POST)
    @ApiOperation(value = "获取所有宠物信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false, paramType = "body"),
            @ApiImplicitParam(name = "pages", value = "页码", dataType = "int", required = false, paramType = "path"),
            @ApiImplicitParam(name = "rows", value = "页大小", dataType = "int", required = false, paramType = "path")
    })
    public AjaxJSON getAllPets(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();

        try {

            if(param.get("pages")!=null||param.get("rows")!=null) {
                int pages = Integer.parseInt(param.get("pages").toString());
                int rows = Integer.parseInt(param.get("rows").toString());
                param.put("firstResult", (pages - 1) * rows);
                param.put("maxResults", rows);
            }
            List<TBPets>tbPetsList=petsMapper.getAllPets(param);
            int total= Integer.parseInt(petsMapper.getTotal().get("total").toString());

            ajaxJSON.setMsg("查询成功");
            ajaxJSON.setObj(tbPetsList);
            ajaxJSON.setTotal(total);

        }catch (Exception e){
            ajaxJSON.setSuccess(false);
            ajaxJSON.setMsg("获取宠物信息失败"+e.getCause());
        }
        return ajaxJSON;
    }

    /**
     * 获取单个宠物信息
     */
    @RequestMapping(value = "getSinglePetInfo",method = RequestMethod.POST)
    @ApiOperation(value = "获取单个宠物信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false, paramType = "body"),
            @ApiImplicitParam(name = "id", value = "宠物ID", dataType = "int", required = true)
    })
    public AjaxJSON getSinglePetInfo(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        TBPets resultMap=  petsMapper.getSinglePetInfo(param);
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
    @ApiOperation(value = "管理员添加宠物")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "petsPrice", value = "宠物价格", dataType = "int", required = true),
            @ApiImplicitParam(name = "petsDesc", value = "宠物描述", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "petsNum", value = "宠物数量", dataType = "int", required = true),
            @ApiImplicitParam(name = "isHot", value = "是否热卖", dataType = "int", required = true),
            @ApiImplicitParam(name = "imgPath", value = "图片路径", dataType = "varchar", required = true)
    })
    public AjaxJSON addPets(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();

        param.put("id",System.currentTimeMillis());
        petsMapper.addPets(param);
        ajaxJSON.setMsg("添加成功");

        return ajaxJSON;
    }

    /**
     * 更新宠物信息
     */
    @RequestMapping(value = "updatePetMsg",method = RequestMethod.PUT)
    @ApiOperation(value = "管理员修改宠物信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "id", value = "宠物ID", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "petsDesc", value = "宠物描述", dataType = "varchar", required = false),
            @ApiImplicitParam(name = "petsNum", value = "宠物数量", dataType = "int", required = false),
            @ApiImplicitParam(name = "petsPrice", value = "宠物价格", dataType = "int", required = false),
            @ApiImplicitParam(name = "isHot", value = "是否热卖", dataType = "int", required = false),
            @ApiImplicitParam(name = "imgPath", value = "图片路径", dataType = "varchar", required = false)
    })
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
    @RequestMapping(value = "delPet",method = RequestMethod.DELETE)
    @ApiOperation(value = "管理员删除宠物信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "id", value = "宠物ID", dataType = "varchar", required = true)
    })
    public AjaxJSON delUsers(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        try {
            petsMapper.delPet(param);
            ajaxJSON.setMsg("删除成功");
        }catch (Exception e){
            ajaxJSON.setSuccess(false);
            ajaxJSON.setMsg("删除失败"+e.getCause());
        }
        return ajaxJSON;
    }

}
