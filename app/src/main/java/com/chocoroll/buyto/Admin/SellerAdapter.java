package com.chocoroll.buyto.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocoroll.buyto.DetailDeal.DetailDealActivity;
import com.chocoroll.buyto.Extra.DownloadImageTask;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.R;
import com.chocoroll.buyto.Seller.DepositFragment;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-05-23.
 */

public class SellerAdapter extends ArrayAdapter<Seller> {
    private ArrayList<Seller> items;
    private Context context;

    public SellerAdapter(Context context, int textViewResourceId, ArrayList<Seller> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_seller_deal, null);
        }
        final Seller p = items.get(position);
        if (p != null) {

            ((TextView) v.findViewById(R.id.sellerID)).setText(p.getId());
            ((TextView) v.findViewById(R.id.sellerNum)).setText(p.getSellerNum());
            ((TextView) v.findViewById(R.id.sellerPhone)).setText(p.getPhone());
            ((TextView) v.findViewById(R.id.sellerOffice)).setText(p.getOffice());

            ((Button) v.findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sendsSellerOK(p.getId());


                }
            });


            ((Button) v.findViewById(R.id.btnNO)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendSellerNO(p.getId());


                }
            });


        }
        return v;
    }

    void sendsSellerOK(String id){


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("판매자를 승인하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("id", id);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendsSellerOK(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);


                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("판매자를 승인하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        });

                            }else{
                                builder.setTitle("판매자 승인을 실패하였습니다.")        // 제목 설정
                                        .setMessage("네트워크를 확인해주세요")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        });

                            }
                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("네트워크가 불안정합니다.")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    });

                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기

                        }
                    });
                } catch (Throwable ex) {

                }
            }
        }).start();

    }

    void sendSellerNO(String id){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("판매자를 거절하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("id", id);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendSellerNO(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);


                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("판매자를 거절하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        });

                            }else{
                                builder.setTitle("판매자 거절을 실패하였습니다.")        // 제목 설정
                                        .setMessage("네트워크를 확인해주세요")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        });

                            }
                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("네트워크가 불안정합니다.")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    });

                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기

                        }
                    });
                } catch (Throwable ex) {

                }
            }
        }).start();
    }
}
