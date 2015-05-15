package com.chocoroll.buyto.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RA on 2015-04-24.
 */
public class Deal implements Parcelable{

    // 상품번호, 이름, 가격
    String num;
    String name;
    String price;

    // 카테고리
    String bCategory;
    String sCategory;

    // 마감날짜, 최대인원
    String dday;
    String maxBook;

    // keep, book 수
    String keep;
    String book;

    // 로고이미지, 상세이미지, 판매자 코멘트
    String thumbnail;
    String detailView;
    String comment;

    // 판매자 정보
    String seller;
    String phone;

    // 상태
    String state;


    public Deal(Parcel in) {
        readFromParcel(in);
    }

    public Deal(String num, String name, String price, String bCategory, String sCategory,
                String dday, String maxBook, String keep, String book,
                String thumbnail, String detailView, String comment,
                String seller, String phone, String state){
        this.num  = num;
        this.name = name;
        this.price= price;

        this.bCategory = bCategory;
        this.sCategory = sCategory;

        this.dday = dday;
        this.maxBook = maxBook;

        this.keep = keep;
        this.book = book;

        this.thumbnail = thumbnail;
        this.detailView =detailView;
        this.comment = comment;

        this.seller= seller;
        this.phone =phone;

        this.state = state;




    }

    public String getNum(){ return num; }
    public String getName(){ return name; }
    public String getbCategory(){ return bCategory; }
    public String getsCategory(){ return sCategory; }
    public String getDday(){ return dday; }
    public String getBook(){ return book; }
    public String getMaxBook(){ return maxBook; }
    public String getSeller(){ return seller; }
    public String getPrice() {return price;}

    public String getComment() {
        return comment;
    }

    public String getPhone() {
        return phone;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDetailView() {
        return detailView;
    }

    public String getState() {
        return state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(num);
        parcel.writeString(name);
        parcel.writeString(price);

        parcel.writeString(bCategory);
        parcel.writeString(sCategory);

        parcel.writeString(dday);
        parcel.writeString(maxBook);

        parcel.writeString(keep);
        parcel.writeString(book);

        parcel.writeString(thumbnail);
        parcel.writeString(detailView);
        parcel.writeString(comment);

        parcel.writeString(seller);
        parcel.writeString(phone);

        parcel.writeString(state);

    }

    private void readFromParcel(Parcel in){
        num = in.readString();
        name = in.readString();
        price=in.readString();

        bCategory = in.readString();
        sCategory = in.readString();

        dday = in.readString();
        maxBook = in.readString();

        keep = in.readString();
        book = in.readString();

        thumbnail=in.readString();
        detailView=in.readString();
        comment =in.readString();

        seller = in.readString();
        phone = in.readString();

        state = in.readString();

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
