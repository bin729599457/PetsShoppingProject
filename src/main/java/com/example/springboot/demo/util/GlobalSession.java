package com.example.springboot.demo.util;

import com.example.springboot.demo.domain.TBUser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GlobalSession {
        private static Map<String, TBUser> USER_SESSION_MAP = new HashMap<>();

        //登陆成功将用户信息存放在key为token的map当中(放在jvm内存)
        public static String loginSuccess(TBUser user) {
            String token = UUID.randomUUID().toString();
            USER_SESSION_MAP.put(token, user);
            return token;
        }

        //根据token从内存中获取用户信息
        public static TBUser getUser(String token) {
            return USER_SESSION_MAP.get(token);
        }

        //登出时根据token将用户信息删除
        public static void logout(String token){
            USER_SESSION_MAP.remove(token);
        }
    }
