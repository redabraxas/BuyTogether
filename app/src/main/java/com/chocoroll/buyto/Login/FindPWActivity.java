package com.chocoroll.buyto.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FindPWActivity extends FragmentActivity {
    ProgressDialog dialog;
    Button send;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        send = (Button)findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                InputMethodManager pad=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                ///로그인 버튼을 누르면 키패드 사라지게 만들기.
                pad.hideSoftInputFromWindow(((EditText) findViewById(R.id.user_email)).getWindowToken(), 0);

                // 아이디 입력 후 임시 비밀번호 발송
                user_email = ((EditText)findViewById(R.id.user_email)).getText().toString();
                if(user_email.length()==0)
                {
                    new AlertDialog.Builder(FindPWActivity.this).setMessage("아이디를 입력해주세요.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
                else{
                    findPasswd();
                }
            }
        });
    }
    private void findPasswd(){


        dialog = new ProgressDialog(FindPWActivity.this);
        dialog.setMessage("정보를 받아오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info=new JsonObject();
        info.addProperty("user_email", user_email);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit login = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    login.find_passwd(info, new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            if(result.equals("failed")){

                                new AlertDialog.Builder(FindPWActivity.this).setMessage("아이디를 다시 확인해주세요.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();

                            }else{
                                new AlertDialog.Builder(FindPWActivity.this).setMessage("임시 비밀번호가 전송되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();

                                            }
                                        }).show();

                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindPWActivity.this);
                            builder.setTitle("네트워크 에러")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요.")        // 메세지 설정
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

