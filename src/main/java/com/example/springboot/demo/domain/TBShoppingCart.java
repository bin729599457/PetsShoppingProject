package com.example.springboot.demo.domain;

import java.util.Date;

public class TBShoppingCart {

    private long id;
    private long userId;
    private long petsId;
    private Integer nums;

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPetsId() {
        return petsId;
    }

    public void setPetsId(long petsId) {
        this.petsId = petsId;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }
}
