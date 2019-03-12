package com.example.springboot.demo.dao;

import com.example.springboot.demo.domain.TBOrder;
import com.example.springboot.demo.domain.TBPets;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface OrderMapper {

    List<TBOrder> getAllOrders(Map param);

    Map getTotal();

    void makeOrder(Map param);

    void addPetsInfo(Map param);

    void updateOrderMsg(Map param);

    TBOrder getOrderDetail(Map param);

    List<TBPets> getOrderPetInfo(Map param);

}
