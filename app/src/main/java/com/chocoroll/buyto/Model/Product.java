package com.chocoroll.buyto.Model;

/**
 * Created by RA on 2015-04-24.
 */
public class Product {
    String name;
    String bCategory;
    String sCategory;
    String dday;
    int count;

    public Product(String name, String bCategory, String sCategory, String dday, int count){
        this.name = name;
        this.bCategory = bCategory;
        this.sCategory = sCategory;
        this.dday = dday;
        this.count = count;

    }

    public String getName(){ return name; }
    public String getbCategory(){ return bCategory; }
    public String getsCategory(){ return sCategory; }
    public String getDday(){ return dday; }
    public int getCount(){ return count; }


}
