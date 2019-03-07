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

    TBUser selectUserByName();

    List<TBUser> getAllUsers();

    void addUser(Map param);

    Map userLogin(Map param);

    Map getSingleUsersInfo(Map param);

    Map forgetPassword(Map param);

    void updateMsg(Map param);

    void delUsers(Map param);
}
