package com.example.springboot.demo.domain;

public class TBPetsOrderInfo {

    private long id;
    private Integer orderId;
    private Integer petsId;
    private Integer petsNums;

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPetsId() {
        return petsId;
    }

    public void setPetsId(Integer petsId) {
        this.petsId = petsId;
    }

    public Integer getPetsNums() {
        return petsNums;
    }

    public void setPetsNums(Integer petsNums) {
        this.petsNums = petsNums;
    }
}
