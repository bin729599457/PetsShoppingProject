package com.example.springboot.demo.dao;

import com.example.springboot.demo.domain.TBUser;
import com.example.springboot.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper {


    List<TBUser> getAllUsers(Map param);

    void addUser(Map param);

    TBUser userLogin(Map param);

    TBUser getSingleUsersInfo(Map param);

    TBUser forgetPassword(Map param);

    Map getTotal();

    void updateMsg(Map param);

    void delUsers(Map param);
}
