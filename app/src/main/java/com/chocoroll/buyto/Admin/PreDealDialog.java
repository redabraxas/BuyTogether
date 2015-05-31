package com.chocoroll.buyto.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chocoroll.buyto.DetailDeal.Answer;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-05-23.
 */
public class PreDealDialog extends Dialog {

    Deal deal;
    Context context;

    public PreDealDialog(Context context, Deal deal) {
        super(context);
        this.context = context;
        this.deal = deal;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_predeal);
        this.setTitle("딜 미리보기");


        ((Button)findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDealResult("ok");
            }
        });

        ((Button)findViewById(R.id.btnNO)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDealResult("no");
            }
        });


    }



    void sendDealResult(String result){

        final String str;
        if(result.equals("ok")){
            str ="승인";
        }else{
            str="거절";
        }


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("딜을 "+str+"하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("dealNum", deal.getNum());
        info.addProperty("result", result);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendDealResult(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);


                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("딜을 "+str+ "하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        });

                            }else{
                                builder.setTitle("딜 "+str+"을 실패하였습니다.")        // 제목 설정
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
