package com.chocoroll.buyto.Mine;

/**
 * Created by RA on 2015-05-23.
 */
public class DealState {
    String dealNum;
    String dealName;
    String thumbnail;
    String bCategory;
    String sCategory;
    String price;

    String stateNum;

    String waiting;

    String deposit;
    String date;

    public DealState(String dealNum, String dealName, String thumbnail, String bCategory, String sCategory, String price, String stateNum, String deposit, String date){
        this.dealNum = dealNum;
        this.dealName =dealName;
        this.thumbnail =thumbnail;
        this.bCategory =bCategory;
        this.sCategory = sCategory;
        this.price=price;
        this.stateNum =stateNum;
        this.deposit =deposit;
        this.date =date;
    }

    public DealState(String dealNum, String dealName, String thumbnail, String bCategory, String sCategory, String price, String stateNum, String waiting){
        this.dealNum = dealNum;
        this.dealName =dealName;
        this.thumbnail =thumbnail;
        this.bCategory =bCategory;
        this.sCategory = sCategory;
        this.price=price;
        this.stateNum =stateNum;
        this.waiting =waiting;
    }


    public String getbCategory() {
        return bCategory;
    }

    public String getDate() {
        return date;
    }

    public String getDealName() {
        return dealName;
    }

    public String getDealNum() {
        return dealNum;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getsCategory() {
        return sCategory;
    }

    public String getStateNum() {
        return stateNum;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public String getWaiting() {
        return waiting;
    }
}
