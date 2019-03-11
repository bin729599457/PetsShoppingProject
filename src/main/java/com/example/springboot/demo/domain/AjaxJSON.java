package com.example.springboot.demo.domain;

import io.swagger.models.auth.In;

import java.util.Map;

public class AjaxJSON {

    private boolean success = true;
    private String msg = "";
    private Object obj = null;

    private int total=0;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }}

