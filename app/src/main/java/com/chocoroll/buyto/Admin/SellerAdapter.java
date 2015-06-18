package com.chocoroll.buyto.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.R;
import com.chocoroll.buyto.Seller.SellerFragment;
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
            v = vi.inflate(R.layout.model_seller, null);
        }
        final Seller p = items.get(position);
        if (p != null) {

            ((TextView) v.findViewById(R.id.sellerID)).setText(p.getId());
            ((TextView) v.findViewById(R.id.sellerNum)).setText("사업자번호: "+p.getSellerNum());
            ((TextView) v.findViewById(R.id.sellerPhone)).setText("폰번호: " +p.getPhone());
            ((TextView) v.findViewById(R.id.sellerOffice)).setText("사무실: "+p.getOffice());

            ((TextView) v.findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sendsSellerResult(p.getId(), "ok");


                }
            });


            ((TextView) v.findViewById(R.id.btnNO)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendsSellerResult(p.getId(), "no");


                }
            });


        }
        return v;
    }

    void sendsSellerResult(String id, String result){


        final String str;
        if(result.equals("ok")){
            str ="승인";
        }else{
            str="거절";
        }


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("판매자를 "+str+"하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("id", id);
        info.addProperty("result", result);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendsSellerResult(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);


                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("판매자를 "+str+"하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                ((MainActivity)MainActivity.mContext).removeAllStack();
                                                FragmentTransaction ft = ((MainActivity)MainActivity.mContext).getSupportFragmentManager().beginTransaction();
                                                ft.replace(R.id.container, new AdminFragment());
                                                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                                                ft.addToBackStack(null);
                                                ft.commit();
                                            }
                                        });

                            }else{
                                builder.setTitle("판매자 "+str+"을 실패하였습니다.")        // 제목 설정
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
