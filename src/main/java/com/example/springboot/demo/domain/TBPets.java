package com.example.springboot.demo.domain;

public class TBPets {

    private long id;
    private String petsDesc;
    private Integer petsPrice;
    private Integer petsNum;
    private String imgPath;
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

    public void setId(long id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }
}
