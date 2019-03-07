package com.example.springboot.demo.domain;

public class TBPets {

    private long id;
    private String petsDesc;
    private Integer petsPrice;
    private Integer petsNum;
    private String petsImgUrl;
    private Integer isHot;

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPetsDesc() {
        return petsDesc;
    }

    public void setPetsDesc(String petsDesc) {
        this.petsDesc = petsDesc;
    }

    public Integer getPetsPrice() {
        return petsPrice;
    }

    public void setPetsPrice(Integer petsPrice) {
        this.petsPrice = petsPrice;
    }

    public Integer getPetsNum() {
        return petsNum;
    }

    public void setPetsNum(Integer petsNum) {
        this.petsNum = petsNum;
    }

    public String getPetsImgUrl() {
        return petsImgUrl;
    }

    public void setPetsImgUrl(String petsImgUrl) {
        this.petsImgUrl = petsImgUrl;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }
}
