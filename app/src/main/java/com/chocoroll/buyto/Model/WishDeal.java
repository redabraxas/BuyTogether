package com.chocoroll.buyto.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RA on 2015-05-15.
 */
public class WishDeal implements Parcelable {


    // 상품번호, 이름, 가격
    String num;
    String name;

    // 카테고리
    String bCategory;
    String sCategory;

    // wish 수
    String wish;

    // 로고이미지, 상세이미지, 판매자 코멘트
    String thumbnail;
    String detailView;
    String comment;

    public WishDeal(Parcel in) {
        readFromParcel(in);
    }

    public WishDeal(String num, String name, String bCategory, String sCategory, String wish, String thumbnail, String detailView){
        this.num  = num;
        this.name = name;

        this.bCategory = bCategory;
        this.sCategory = sCategory;

        this.wish = wish;

        this.thumbnail = thumbnail;
        this.detailView = detailView;

    }

    public String getNum(){ return num; }
    public String getName(){ return name; }
    public String getbCategory(){ return bCategory; }
    public String getsCategory(){ return sCategory; }

    public String getWish() {
        return wish;
    }

    public String getDetailView() {
        return detailView;
    }

    public String getThumbnail() {
        return thumbnail;
    }

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

        parcel.writeString(wish);

        parcel.writeString(thumbnail);
        parcel.writeString(detailView);
    }

    private void readFromParcel(Parcel in){
        num = in.readString();
        name = in.readString();

        bCategory = in.readString();
        sCategory = in.readString();

        wish = in.readString();

        thumbnail=in.readString();
        detailView=in.readString();

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
