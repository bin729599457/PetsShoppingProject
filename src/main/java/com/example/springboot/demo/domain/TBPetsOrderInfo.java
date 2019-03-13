package com.example.springboot.demo.domain;

public class TBPetsOrderInfo {

    private long id;
    private long orderId;
    private long petsId;
    private Integer petsNums;

    public TBPetsOrderInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getPetsId() {
        return petsId;
    }

    public void setPetsId(long petsId) {
        this.petsId = petsId;
    }

    public Integer getPetsNums() {
        return petsNums;
    }

    public void setPetsNums(Integer petsNums) {
        this.petsNums = petsNums;
    }
}
