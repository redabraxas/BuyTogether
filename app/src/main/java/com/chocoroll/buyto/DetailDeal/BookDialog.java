package com.chocoroll.buyto.DetailDeal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-05-16.
 */
public class BookDialog  extends Dialog {

    ProgressDialog dialog;

    String dealNum;
    String userID;

    Context context;



    public BookDialog(Context context, String dealNum, String userID) {
        super(context);
        this.context = context;
        this.dealNum = dealNum;
        this.userID = userID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_book);
        this.setTitle("딜 신청");

        TextView replyAnswer = (TextView) findViewById(R.id.btnBook);
        replyAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = ((EditText) findViewById(R.id.book_name)).getText().toString();
                String addr = ((EditText) findViewById(R.id.book_addr)).getText().toString();
                String phone = ((EditText) findViewById(R.id.book_phone)).getText().toString();

                if(name.equals("") || addr.equals("") || phone.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("작성 실패")        // 제목 설정
                            .setMessage("모든 칸을 입력해주세요~")        // 메세지 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                // 확인 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }else{


                    sendBookDeal(name, addr, phone);
                    dismiss();
                }


            }
        });



    }




    void sendBookDeal(String name, String addr, String phone){
        dialog = new ProgressDialog(context);
        dialog.setMessage("딜을 신청하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();

        info.addProperty("id", userID);
        info.addProperty("dealNum", dealNum);
        info.addProperty("deposit", name);
        info.addProperty("address", addr);
        info.addProperty("phone", phone);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendBookDeal(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();

                            if(result.equals("success")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("이 딜의 신청이 완료되었습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        });
                                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                                dialog.show();    // 알림창 띄우기

                            }else if(result.equals("failed")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("실패")        // 제목 설정
                                        .setMessage("이미 딜을 신청하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        });
                                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                                dialog.show();    // 알림창 띄우기
                            }


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
                }
                catch (Throwable ex) {

                }
            }
        }).start();

    }



}
