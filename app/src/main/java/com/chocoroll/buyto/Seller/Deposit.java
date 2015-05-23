package com.chocoroll.buyto.Seller;

/**
 * Created by RA on 2015-05-23.
 */
public class Deposit {

    private String dealNum;
    private String id;
    private String name;
    private String phone;
    private String address;
    private int state;

    Deposit(String dealNum, String id, String name, String phone, String address, int state){
        this.dealNum =dealNum;
        this.id = id;
        this.name =name;
        this.phone = phone;
        this.address =address;
        this.state =state;
    }

    public int getState() {
        return state;
    }

    public String getDealNum() {
        return dealNum;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
