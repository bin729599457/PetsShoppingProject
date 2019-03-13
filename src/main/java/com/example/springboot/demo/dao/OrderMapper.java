package com.example.springboot.demo.dao;

import com.example.springboot.demo.domain.TBOrder;
import com.example.springboot.demo.domain.TBPets;
import com.example.springboot.demo.domain.TBPetsOrderInfo;
import com.example.springboot.demo.domain.TBSalesCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface OrderMapper {

    List<TBOrder> getAllOrders(Map param);

    Map getTotal(Map param);

    void makeOrder(Map param);

    void addPetsInfo(Map param);

    void updateOrderMsg(Map param);

    TBOrder getOrderDetail(Map param);

    List<TBPets> getOrderPetInfo(Map param);

    List<TBPetsOrderInfo> selectPetsOrderInfoById(Map param);

    void delOrder(Map param);

    void delOrderPetInfo(Map param);

    void creteSalesCount(Map param);

    void updateSalesCount(Map param);

    Map salesCount();

    Map isExistCount(Map param);
}
