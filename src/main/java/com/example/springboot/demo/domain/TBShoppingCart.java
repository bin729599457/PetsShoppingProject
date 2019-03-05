package com.example.springboot.demo.domain;

import java.util.Date;

public class TBShoppingCart {

    private long id;
    private Integer userId;
    private Integer petsId;
    private Integer nums;

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPetsId() {
        return petsId;
    }

    public void setPetsId(Integer petsId) {
        this.petsId = petsId;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }
}
