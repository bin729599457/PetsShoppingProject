package com.example.springboot.demo.dao;

import com.example.springboot.demo.domain.TBPets;
import com.example.springboot.demo.domain.TBUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PetsMapper {

    List<TBPets> getAllPets();

    void addPets(Map param);

    void updatePetMsg(Map param);

    Map getSinglePetInfo(Map param);

    void delPet(Map param);

}
