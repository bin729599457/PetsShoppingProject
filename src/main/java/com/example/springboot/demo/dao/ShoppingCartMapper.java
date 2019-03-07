package com.example.springboot.demo.dao;

import com.example.springboot.demo.domain.TBPets;
import com.example.springboot.demo.domain.TBShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ShoppingCartMapper {

    List<TBShoppingCart> getCartMsgById(Map param);

    void addCart(Map param);

    void delPetFromCart(Map param);

}
