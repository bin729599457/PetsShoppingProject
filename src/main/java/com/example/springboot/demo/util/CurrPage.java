package com.example.springboot.demo.util;

import java.util.List;

public class CurrPage {

    public static List queryByPage(List sortList,int currpage,int pageSize){
        int firstIndex=(currpage-1)*pageSize;
        int lastIndex=currpage*pageSize;
        if(lastIndex>=sortList.size()){
            return sortList.subList(firstIndex,(sortList.size()-1));
        }
        return sortList.subList(firstIndex,lastIndex);

    }

}
