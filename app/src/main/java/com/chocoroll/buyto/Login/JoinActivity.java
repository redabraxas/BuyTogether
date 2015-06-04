package com.chocoroll.buyto.Login;

//
//import java.util.ArrayList;
//
//import retrofit.Callback;
//import retrofit.RestAdapter;
//import retrofit.RetrofitError;
//import retrofit.client.Response;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.R;
import com.google.android.gcm.GCMRegistrar;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class JoinActivity extends Activity {

    private ProgressDialog dialog;
    private  String passwd_comfirm;
    private String passwd;
    private String email;

    private String regExpStr = "^([a-z]+[0-9]+[a-z0-9]*|[0-9]+[a-z]+[a-z0-9]*)$";
    private String emailregex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
    private String idregex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{5,11}$";

    boolean seller_flag = false;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        // TODO Auto-generated method stub
//        final EditText edit_id = (EditText)findViewById(R.id.edit_join_id);
        final EditText edit_pw = (EditText)findViewById(R.id.edit_join_pw);
        final EditText edit_pwconfirm = (EditText)findViewById(R.id.edit_join_pwconfirm);
        final EditText edit_email = (EditText)findViewById(R.id.edit_join_email);


        // 회원가입
        Button btn_ok = (Button)findViewById(R.id.btn_join_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                passwd_comfirm = edit_pwconfirm.getText().toString();
                passwd = edit_pw.getText().toString();
                email = edit_email.getText().toString();
                //여기에 패스워드 길이랑 패턴 확인하는구문

                if(!email.matches(idregex))
                {
                    AlertDialog dialog2 = new AlertDialog.Builder(JoinActivity.this).setMessage("잘못된 아이디입니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    dia.dismiss();
                                }
                            }).show();
                    TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                    Typeface face=Typeface.SANS_SERIF;
                    textView.setTypeface(face);
                }
                else if(!email.matches(emailregex))
                {
                    AlertDialog dialog2 = new AlertDialog.Builder(JoinActivity.this).setMessage("잘못된 이메일입니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    dia.dismiss();
                                }
                            }).show();
                    TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                    Typeface face=Typeface.SANS_SERIF;
                    textView.setTypeface(face);
                }else if(passwd.length()<5 || !passwd.matches(regExpStr)){
                    AlertDialog dialog2 = new AlertDialog.Builder(JoinActivity.this).setMessage("비밀번호는 6자이상이고 영어/숫자 혼합이어야합니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    dia.dismiss();
                                }
                            }).show();
                    TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                    Typeface face=Typeface.SANS_SERIF;
                    textView.setTypeface(face);

                }else if(!passwd_comfirm.equals(passwd)){
                    AlertDialog dialog2 = new AlertDialog.Builder(JoinActivity.this).setMessage("비밀번호가 일치하지 않습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    dia.dismiss();
                                }
                            }).show();
                    TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                    Typeface face=Typeface.SANS_SERIF;
                    textView.setTypeface(face);

                }else
                {

                    // 만약 판매자이면,
                    if(seller_flag){

                        String sellerNum = ((EditText) findViewById(R.id.Seller_edit_join_compnum)).getText().toString();
                        String sellerAddr = ((EditText) findViewById(R.id.Seller_edit_join_compadd)).getText().toString();
                        String sellerPhone = ((EditText) findViewById(R.id.Seller_edit_join_compphone)).getText().toString();

                        if(sellerNum.equals("") || sellerAddr.equals("") || sellerPhone.equals("")){
                             new AlertDialog.Builder(JoinActivity.this).setMessage("빈칸을 확인해주세요!!")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dia, int which) {

                                        }
                                    }).show();

                        }else{
                            RegistPhoneID task1 = new RegistPhoneID();
                            task1.execute(JoinActivity.this);
                        }


                    }else{
                        RegistPhoneID task1 = new RegistPhoneID();
                        task1.execute(JoinActivity.this);
                    }


                }
            }
        });

        CheckBox checkbox = (CheckBox) findViewById(R.id.seller_check);
        final LinearLayout Layout = (LinearLayout) findViewById(R.id.seller_form);

        Layout.setVisibility(View.GONE);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if (isChecked){

                    seller_flag = true;
                    Layout.setVisibility(View.VISIBLE);
                }

                else{
                    seller_flag = false;
                    Layout.setVisibility(View.GONE);

                }

            }
        });


    }

    void join(final String phoneID){
        dialog = new ProgressDialog(JoinActivity.this);
        dialog.setMessage("회원가입 요청중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info=new JsonObject();

        info.addProperty("phoneID", phoneID);
        info.addProperty("pw", passwd);
        info.addProperty("id", email);

        new Thread(new Runnable() {
            public void run() {

                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit join = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    join.join(info,new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            dialog.dismiss();
                            if(result.equals("success"))
                                finish();
                            else
                            {
                                new AlertDialog.Builder(JoinActivity.this).setMessage("알수 없는 이유로 회원가입에 실패하였습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dia, int which) {
                                                dia.dismiss();
                                            }
                                        }).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            new AlertDialog.Builder(JoinActivity.this).setMessage("네트워크 상태를 확인해주세요.")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dia, int which) {
                                            dia.dismiss();
                                        }
                                    }).show();
                        }
                    });
                }
                catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }





    // 처음 실행 시 기기 아이디를 등록한다!!
    public class RegistPhoneID extends AsyncTask<Context , Integer , String> {

        String PROJECT_ID = "988805512295";
        @Override
        protected String doInBackground(Context... params) {
            // TODO Auto-generated method stub
            String regId;

            GCMRegistrar.checkDevice(params[0]);
            GCMRegistrar.checkManifest(params[0]);

            regId = GCMRegistrar.getRegistrationId(params[0]);

            if (regId.equals("")) {
                GCMRegistrar.register(params[0], PROJECT_ID);
                regId = GCMRegistrar.getRegistrationId(params[0]);
            }

            return regId;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            join(result);


        }

    }

}
