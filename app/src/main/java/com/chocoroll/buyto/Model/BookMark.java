package com.chocoroll.buyto.Model;

/**
 * Created by RA on 2015-05-31.
 */
public class BookMark {
    String bCategory;
    String sCategory;

    public BookMark(String bCategory, String sCategory){
        this.bCategory = bCategory;
        this.sCategory =sCategory;
    }

    public String getbCategory() {
        return bCategory;
    }

    public String getsCategory() {
        return sCategory;
    }
}
