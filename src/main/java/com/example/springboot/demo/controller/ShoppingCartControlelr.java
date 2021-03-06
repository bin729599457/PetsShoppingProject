package com.example.springboot.demo.controller;

import com.example.springboot.demo.dao.ShoppingCartMapper;
import com.example.springboot.demo.domain.AjaxJSON;
import com.example.springboot.demo.domain.TBPets;
import com.example.springboot.demo.domain.TBShoppingCart;
import com.example.springboot.demo.util.CurrPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartControlelr {

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    /**
     * 用户将宠物加入购物车
     * @param param
     * @return
     */
    @RequestMapping(value = "addCart",method = RequestMethod.POST)
    @ApiOperation(value = "用户添加宠物进入购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "petsId", value = "宠物ID", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "nums", value = "商品数量", dataType = "int", required = true)
    })
    public AjaxJSON addCart(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        try {

            //先进行查询用户购物车是否已经存在该商品
            Map isExist=shoppingCartMapper.isExistPets(param);
            //非空，购物车已经存在该商品
            if(isExist!=null){
                int num= (int) isExist.get("nums");
                int addNum= Integer.parseInt(param.get("nums").toString());
                param.put("nums",num+addNum);
                shoppingCartMapper.updateCartNums(param);
                ajaxJSON.setMsg("添加购物车成功");
            }else {
                param.put("id",System.currentTimeMillis());
                shoppingCartMapper.addCart(param);
                ajaxJSON.setMsg("添加购物车成功");
            }

        }catch (Exception e){
            ajaxJSON.setSuccess(false);
            ajaxJSON.setMsg("添加购物车失败"+e.getCause());
        }

        return ajaxJSON;
    }


    /**
     * 获取用户购物车信息
     * @param param
     * @return
     */
    @RequestMapping(value = "getCartMsgById",method = RequestMethod.POST)
    @ApiOperation(value = "用户查看购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "varchar", required = true)
    })
    public AjaxJSON getCartMsgById(@RequestParam Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();

        try {

            if(param.get("pages")!=null||param.get("rows")!=null) {
                int pages = Integer.parseInt(param.get("pages").toString());
                int rows = Integer.parseInt(param.get("rows").toString());
                param.put("firstResult", (pages - 1) * rows);
                param.put("maxResults", rows);
            }
            List<Map> tbShoppingCarts=shoppingCartMapper.getCartMsgById(param);
            int total= Integer.parseInt(shoppingCartMapper.getTotal().get("total").toString());

            ajaxJSON.setObj(tbShoppingCarts);
            ajaxJSON.setTotal(total);
            ajaxJSON.setMsg("查询成功");
        }catch (Exception e){
            ajaxJSON.setSuccess(false);
            ajaxJSON.setMsg("查询购物车信息失败"+e.getCause());
        }

        return ajaxJSON;
    }

    /**
     * 删除购物车宠物
     */
    @ApiOperation(value = "用户删除购物车宠物")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "petsId", value = "宠物ID", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "varchar", required = true)
    })
    @RequestMapping(value = "delPetFromCart",method = RequestMethod.DELETE)
    public AjaxJSON delPetFromCart(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        try {
            shoppingCartMapper.delPetFromCart(param);
            ajaxJSON.setMsg("删除成功");
        }catch (Exception e){
            ajaxJSON.setSuccess(false);
            ajaxJSON.setMsg("删除失败"+e.getCause());
        }
        return ajaxJSON;
    }

    /**
     * 更新购物车信息（修改购物车商品数量）
     */
    @RequestMapping(value = "updateCartNums",method = RequestMethod.PUT)
    @ApiOperation(value = "更新购物车信息（修改购物车商品数量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "petsId", value = "宠物ID", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "nums", value = "数量", dataType = "int", required = true)
    })
    public AjaxJSON updateCartNums(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        try {
            shoppingCartMapper.updateCartNums(param);
            ajaxJSON.setMsg("修改成功");
        }catch (Exception e){
            ajaxJSON.setMsg("修改失败"+e.getCause());
            ajaxJSON.setSuccess(false);
        }

        return ajaxJSON;
    }

}
