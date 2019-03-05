package com.example.springboot.demo.service;

import com.example.springboot.demo.domain.TBUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


public class UserServiceImpl implements UserService {

    @Resource
    private TBUser tbUser;


    @Override
    public String addUser(TBUser tbUser) {

        return null;
    }

    @Override
    public String Login(String code) {
        return null;
    }

    @Override
    public String checkLogin(String trd_session) {
        return null;
    }

    @Override
    public String change(String trd_session, String method, String value) {
        return null;
    }

    @Override
    public int getUserPassword(String trd_session) {
        return 0;
    }

    @Override
    public boolean checkUserPassword(String trd_session, String input_password) {
        return false;
    }
}
