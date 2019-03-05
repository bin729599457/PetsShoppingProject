package com.example.springboot.demo.service;

import com.example.springboot.demo.domain.TBUser;

public interface UserService {

    public String addUser(TBUser tbUser);

    public String Login(String code);

    public String checkLogin(String trd_session);

    public String change(String trd_session,String method,String value);

    public int getUserPassword(String trd_session);

    public boolean checkUserPassword(String trd_session,String input_password);
}
