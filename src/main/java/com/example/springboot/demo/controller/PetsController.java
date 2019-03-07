package com.example.springboot.demo.controller;


import com.example.springboot.demo.dao.PetsMapper;
import com.example.springboot.demo.domain.AjaxJSON;
import com.example.springboot.demo.domain.TBPets;
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

    @RequestMapping(value = "getAllPets",method = RequestMethod.GET)
    public AjaxJSON getAllPets(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        List<TBPets>tbPetsList=petsMapper.getAllPets();
        ajaxJSON.setObj(tbPetsList);
        ajaxJSON.setMsg("查询成功");

        return ajaxJSON;
    }

    @RequestMapping(value = "addPets",method = RequestMethod.POST)
    public AjaxJSON addPets(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();

        try {
            param.put("imgPath", ResourceUtils.getFile("classpath:img").toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        param.put("id",System.currentTimeMillis());
        petsMapper.addPets(param);
        ajaxJSON.setMsg("添加成功");

        return ajaxJSON;
    }
}
