package com.chocoroll.buyto.Admin;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RA on 2015-05-23.
 */
public class Seller implements Parcelable{
    private String id;
    private String sellerNum;
    private String phone;
    private String office;

    public Seller(String id, String sellerNum, String phone, String office){
        this.id =id;
        this.sellerNum = sellerNum;
        this.phone =phone;
        this.office = office;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public String getOffice() {
        return office;
    }

    public String getSellerNum() {
        return sellerNum;
    }

    public Seller(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(sellerNum);
        parcel.writeString(phone);
        parcel.writeString(office);

    }

    private void readFromParcel(Parcel in){
        id = in.readString();
        sellerNum = in.readString();
        phone=in.readString();
        office = in.readString();
    }



    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Seller createFromParcel(Parcel in) {
            return new Seller(in);
        }

        public Seller[] newArray(int size) {
            return new Seller[size];
        }
    };
}
