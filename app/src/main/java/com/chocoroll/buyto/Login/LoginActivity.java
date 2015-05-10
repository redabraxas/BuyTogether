package com.chocoroll.buyto.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chocoroll.buyto.R;

public class LoginActivity extends Activity {
    ProgressDialog dialog;
    String id;
    String passwd;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button join = (Button) findViewById(R.id.login_joinbt);
        join.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });


        Button findId = (Button) findViewById(R.id.login_findid);
        findId.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, FindIdActivity.class);
//                startActivity(intent);
            }
        });

        Button findPw = (Button) findViewById(R.id.login_findpasswd);
        findPw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, FindPwActivity.class);
//                startActivity(intent);
            }
        });


        Button loginbt = (Button) findViewById( R.id.login_bt ) ;
        loginbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                id = ((EditText) findViewById(R.id.login_id)).getText().toString() ;
                passwd = ((EditText) findViewById(R.id.login_passwd)).getText().toString() ;

                InputMethodManager pad=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                ///로그인 버튼을 누르면 키패드 사라지게 만들기.
                pad.hideSoftInputFromWindow(((EditText) findViewById(R.id.login_passwd)).getWindowToken(), 0);
                if(id.length()==0)
                {
                    AlertDialog dialog2 = new AlertDialog.Builder(LoginActivity.this).setMessage("아이디를 입력해주세요.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).show();
                    TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                    Typeface face=Typeface.SANS_SERIF;
                    textView.setTypeface(face);
                }
                else if(passwd.length()==0)
                {
                    AlertDialog dialog2 = new AlertDialog.Builder(LoginActivity.this).setMessage("비밀번호를 입력해주세요.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).show();
                    TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                    Typeface face=Typeface.SANS_SERIF;
                    textView.setTypeface(face);
                }
//                else{
//                    JsonObject info=new JsonObject();
//                    info.addProperty("id", id);
//                    info.addProperty("passwd", passwd);
//                    dialog = new ProgressDialog(LoginActivity.this);
//                    dialog.setMessage("로그인 정보를 받아오는 중입니다...");
//                    dialog.setIndeterminate(true);
//                    dialog.setCancelable(false);
//                    dialog.show();
//                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
//                    Typeface face=Typeface.SANS_SERIF;
//                    textView.setTypeface(face);
//                    Login(info);
//                }
            }
        });
    }
//
//    private void Login(final JsonObject info){
//        new Thread(new Runnable() {
//            public void run() {
//                // TODO Auto-generated method stub
//                try {
//
//                    RestAdapter restAdapter = new RestAdapter.Builder()
//                            .setEndpoint(Retrofit.ROOT)  //call your base url
//                            .build();
//
//
//                    Retrofit mylogin = restAdapter.create(Retrofit.class); //this is how retrofit create your api
//                    mylogin.login(info,new Callback<String>() {
//                        @Override
//                        public void success(String s, Response response) {
//                            if(s.equals("UserNotFound"))
//                            {
//                                dialog.dismiss();
//                                AlertDialog dialog2 = new AlertDialog.Builder(LoginActivity.this).setMessage("아이디를 다시 확인해주세요.")
//                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//
//                                            }
//                                        }).show();
//                                TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
//                                Typeface face=Typeface.SANS_SERIF;
//                                textView.setTypeface(face);
//                            }
//                            else if(s.equals("PasswordError"))
//                            {
//                                dialog.dismiss();
//                                AlertDialog dialog2 = new AlertDialog.Builder(LoginActivity.this).setMessage("비밀번호를 다시 확인해주세요.")
//                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//
//                                            }
//                                        }).show();
//                                TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
//                                Typeface face=Typeface.SANS_SERIF;
//                                textView.setTypeface(face);
//                            }
//                            else
//                            {
//
//                                // 자동로그인에 체크가 되어있따면
//                                CheckBox check_auto = (CheckBox)findViewById(R.id.auto_login);
//                                if(check_auto.isChecked()){
//                                    SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = setting.edit();
//
//                                    editor.putString("id", id);
//                                    editor.putBoolean("auto_login", true);
//                                    editor.commit();
//                                }
//
//
//                                ((MainActivity)MainActivity.m_Context).setUserId(id);
//                                ((MainActivity)MainActivity.m_Context).getBookmark();
//                                if(s.equals("User"))
//                                {
//                                    ((MainActivity)MainActivity.m_Context).userLogin(MainActivity.USER);
//                                }
//                                else if(s.equals("Manager"))
//                                {
//                                    ((MainActivity)MainActivity.m_Context).userLogin(MainActivity.MANAGER);
//                                }
//                                else
//                                {
//                                    ((MainActivity)MainActivity.m_Context).userLogin(s,MainActivity.ARTIST);
//                                }
//                                finish();
//                            }
//
//
//                        }
//
//                        @Override
//                        public void failure(RetrofitError retrofitError) {
//                            dialog.dismiss();
//                            AlertDialog dialog2 = new AlertDialog.Builder(LoginActivity.this).setMessage("네트워크 상태를 확인해주세요.")
//                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dia, int which) {
//                                            dia.dismiss();
//                                        }
//                                    }).show();
//                            TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
//                            Typeface face=Typeface.SANS_SERIF;
//                            textView.setTypeface(face);
//                        }
//
//
//                    });
//                }
//                catch (Throwable ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }).start();
//    }
}


