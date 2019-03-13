package com.example.springboot.demo.domain;

public class TBSalesCount {

    private long id;
    private long petsId;
    private int salesCount;

    public TBSalesCount() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPetsId() {
        return petsId;
    }

    public void setPetsId(long petsId) {
        this.petsId = petsId;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }
}
