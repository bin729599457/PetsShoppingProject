package com.example.springboot.demo.dao;

import com.example.springboot.demo.domain.TBPets;
import com.example.springboot.demo.domain.TBShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sun.nio.cs.ext.MacArabic;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ShoppingCartMapper {

    List<Map> getCartMsgById(Map param);

    void addCart(Map param);

    void delPetFromCart(Map param);

    Map getTotal();

    void updateCartNums(Map param);

    Map isExistPets(Map param);

}
