package com.chocoroll.buyto.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RA on 2015-04-24.
 */
public class Deal implements Parcelable{
    String num;
    String name;
    String bCategory;
    String sCategory;
    String dday;
    String curCount;
    String maxCount;

    String price;
    String seller;


    public Deal(Parcel in) {
        readFromParcel(in);
    }

    public Deal(String name, String bCategory, String sCategory, String dday, String curCount, String maxCount,String seller,String price){
        this.num  = num;
        this.name = name;
        this.bCategory = bCategory;
        this.sCategory = sCategory;
        this.dday = dday;
        this.curCount = curCount;
        this.maxCount=maxCount;
        this.seller= seller;
        this.price= price;

    }

    public String getNum(){ return num; }
    public String getName(){ return name; }
    public String getbCategory(){ return bCategory; }
    public String getsCategory(){ return sCategory; }
    public String getDday(){ return dday; }
    public String getCurCount(){ return curCount; }
    public String getMaxCount(){ return maxCount; }
    public String getSeller(){ return seller; }
    public String getPrice() {return price;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(num);
        parcel.writeString(name);
        parcel.writeString(bCategory);
        parcel.writeString(sCategory);
        parcel.writeString(dday);
        parcel.writeString(curCount);
        parcel.writeString(maxCount);
        parcel.writeString(seller);
        parcel.writeString(price);
    }

    private void readFromParcel(Parcel in){
        num = in.readString();
        name = in.readString();
        bCategory = in.readString();
        sCategory = in.readString();
        dday = in.readString();
        curCount = in.readString();
        maxCount = in.readString();
        seller = in.readString();
        price=in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Deal createFromParcel(Parcel in) {
            return new Deal(in);
        }

        public Deal[] newArray(int size) {
            return new Deal[size];
        }
    };
}
