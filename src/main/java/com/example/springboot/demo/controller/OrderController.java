package com.example.springboot.demo.controller;

import com.example.springboot.demo.dao.OrderMapper;
import com.example.springboot.demo.domain.AjaxJSON;
import com.example.springboot.demo.domain.TBOrder;
import com.example.springboot.demo.domain.TBPets;
import com.example.springboot.demo.domain.TBUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderMapper orderMapper;

    /**
     * 用户下订单
     * @param param
     * @return
     */
    @RequestMapping(value = "makeOrder",method = RequestMethod.POST)
    @ApiOperation(value = "用户添加宠物进入购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "customerId", value = "用户ID", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "sumPrice", value = "订单总价格", dataType = "int", required = true),
            @ApiImplicitParam(name = "address", value = "订单送货地址", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "cartList", value = "订单商品对应数量集合", dataType = "List<Map<String,String>>", required = true),
            @ApiImplicitParam(name = "phone", value = "订单电话", dataType = "varchar", required = true),
            @ApiImplicitParam(name = "receiverName", value = "收货人姓名", dataType = "varchar", required = true)
    })
    public AjaxJSON makeOrder(@RequestBody Map<String,Object> param){

        AjaxJSON ajaxJSON=new AjaxJSON();
        try {

            long orderId=System.currentTimeMillis();
            param.put("id",orderId);
            param.put("status",1);
            param.put("addTime",new Date());
            orderMapper.makeOrder(param);

            ArrayList<HashMap<String,String>> infoList= (ArrayList) param.get("cartList");
            for (HashMap map:infoList){
                Map<String,Object> paramMap=new HashMap<>();
                long petsId= Long.parseLong(map.get("petsId").toString());
                int petsNums=Integer.parseInt(map.get("petsNums").toString());
                paramMap.put("id",System.currentTimeMillis());
                paramMap.put("orderId",orderId);
                paramMap.put("petsId",petsId);
                paramMap.put("petsNums",petsNums);
                orderMapper.addPetsInfo(paramMap);
            }
            ajaxJSON.setMsg("订单新建成功");
            ajaxJSON.setSuccess(true);


        }catch (Exception e){
            ajaxJSON.setSuccess(false);
            ajaxJSON.setMsg("订单新建失败"+e.getCause());
        }

        return ajaxJSON;
    }

    /**
     * 获取所有订单信息
     */
    @ApiOperation(value = "所有订单信息",notes = "若传入customerId则查看用户的所有订单，不传则管理员查看所有用户订单")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false, paramType = "body"),
            @ApiImplicitParam(name = "pages", value = "页码", dataType = "int", required = false),
            @ApiImplicitParam(name = "rows", value = "页大小", dataType = "int", required = false),
            @ApiImplicitParam(name = "customerId", value = "用户ID", dataType = "varchar", required = true)
    })
    @RequestMapping(value = "getAllOrders",method = RequestMethod.POST)
    public AjaxJSON getAllOrders(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        List<TBOrder> tbOrders=new ArrayList<>();
        if(param.get("pages")!=null||param.get("rows")!=null){
            int pages= Integer.parseInt(param.get("pages").toString());
            int rows= Integer.parseInt(param.get("rows").toString());
            param.put("firstResult",(pages-1)*rows);
            param.put("maxResults",rows);
        }
        tbOrders=orderMapper.getAllOrders(param);
        int total= Integer.parseInt(orderMapper.getTotal().get("total").toString());

        ajaxJSON.setObj(tbOrders);
        ajaxJSON.setTotal(total);
        ajaxJSON.setSuccess(true);
        ajaxJSON.setMsg("查询成功");
        return ajaxJSON;
    }


    /**
     * 获取订单详细信息
     */
    @ApiOperation(value = "获取订单详细信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false, paramType = "body"),
            @ApiImplicitParam(name = "rows", value = "页大小", dataType = "int", required = false),
            @ApiImplicitParam(name = "orderId", value = "订单ID", dataType = "varchar", required = true)
    })
    @RequestMapping(value = "getOrderDetail",method = RequestMethod.POST)
    public AjaxJSON getOrderDetail(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        if(param.get("pages")!=null||param.get("rows")!=null){
            int pages= Integer.parseInt(param.get("pages").toString());
            int rows= Integer.parseInt(param.get("rows").toString());
            param.put("firstResult",(pages-1)*rows);
            param.put("maxResults",rows);
        }
        TBOrder tbOrder=orderMapper.getOrderDetail(param);
        List<TBPets> tbPets=orderMapper.getOrderPetInfo(param);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("orderMsg",tbOrder);
        resultMap.put("orderPetInfo",tbPets);

        ajaxJSON.setObj(resultMap);
        ajaxJSON.setSuccess(true);
        ajaxJSON.setMsg("查询成功");
        return ajaxJSON;
    }



    /**
     * 管理员修改订单信息
     */
    @ApiOperation(value = "管理员修改订单信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "param",required = false),
            @ApiImplicitParam(name = "orderId", value = "订单ID", dataType = "varchar", required = true,paramType = "JSON"),
            @ApiImplicitParam(name = "address", value = "订单地址", dataType = "varchar", required = false,paramType = "JSON"),
            @ApiImplicitParam(name = "phone", value = "订单收货人电话", dataType = "varchar", required = false,paramType = "JSON"),
            @ApiImplicitParam(name = "receiverName", value = "收货人名字", dataType = "varchar", required = false,paramType = "JSON"),
            @ApiImplicitParam(name = "status", value = "订单状态", dataType = "varchar", required = false, paramType = "body")
    })
    @RequestMapping(value = "updateOrders",method = RequestMethod.PUT)
    public AjaxJSON updateOrders(@RequestParam Map<String,Object> param){
        AjaxJSON ajaxJSON=new AjaxJSON();

        try {

            orderMapper.updateOrderMsg(param);

            ajaxJSON.setMsg("修改成功");
        }catch (Exception e){
            ajaxJSON.setMsg("修改失败"+e.getCause());
            ajaxJSON.setSuccess(false);
        }

        return ajaxJSON;
    }

}
