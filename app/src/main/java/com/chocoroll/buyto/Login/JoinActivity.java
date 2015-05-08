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
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.chocoroll.buyto.R;
//
//import com.astuetz.PagerSlidingTabStrip;
//import com.example.hangang.R;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.hangang.model.Notice;
//import com.hangang.notice.Notice_BackFragment.MyPagerAdapter;
//import com.hangang.retrofitinterface.Retrofit;


public class JoinActivity extends Activity {
    boolean dupl_flag = false;
    private ProgressDialog dialog;
    private String id ;
    private String passwd;
    private String name;
    private String email;
    private boolean passwdvalid;
    private boolean passwdmatch;
    private String regExpStr = "^([a-z]+[0-9]+[a-z0-9]*|[0-9]+[a-z]+[a-z0-9]*)$";
    private String emailregex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
    private String idregex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{5,11}$";
    private String nameregex="^[a-zA-Z]*$";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        // TODO Auto-generated method stub
//        final EditText edit_id = (EditText)findViewById(R.id.edit_join_id);
        final EditText edit_pw = (EditText)findViewById(R.id.edit_join_pw);
        final EditText edit_pwconfirm = (EditText)findViewById(R.id.edit_join_pwconfirm);
        final EditText edit_name = (EditText)findViewById(R.id.edit_join_name);
        final EditText edit_email = (EditText)findViewById(R.id.edit_join_email);
        // 회원가입
        ((EditText)JoinActivity.this.findViewById(R.id.edit_join_pwconfirm)).addTextChangedListener(MatchTextWatcher);
        ((EditText)JoinActivity.this.findViewById(R.id.edit_join_pw)).addTextChangedListener(ValidtyTextWatcher);
        Button btn_ok = (Button)findViewById(R.id.btn_join_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                id = edit_id.getText().toString();
                passwd = edit_pw.getText().toString();
                name = edit_name.getText().toString();
                email = edit_email.getText().toString();
                //여기에 패스워드 길이랑 패턴 확인하는구문

                if(!id.matches(idregex))
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
                else if(!passwdmatch||!passwdvalid)
                {
                    AlertDialog dialog2 = new AlertDialog.Builder(JoinActivity.this).setMessage("비밀번호를 확인해주세요.")
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
                else if(!name.matches(nameregex))
                {
                    AlertDialog dialog2 = new AlertDialog.Builder(JoinActivity.this).setMessage("이름을 확인해주세요.")
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
                }



//                else
//                {
//                    dialog = new ProgressDialog(JoinActivity.this);
//                    dialog.setMessage("회원가입 요청중입니다...");
//                    dialog.setIndeterminate(true);
//                    dialog.setCancelable(false);
//                    dialog.show();
//                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
//                    Typeface face=Typeface.SANS_SERIF;
//                    textView.setTypeface(face);
//                    new Thread(new Runnable() {
//                        public void run() {
//                            try {
//                                JsonObject info=new JsonObject();
//                                info.addProperty("id", id);
//                                info.addProperty("passwd", passwd);
//                                info.addProperty("name", name);
//                                info.addProperty("email", email);
//                                RestAdapter restAdapter = new RestAdapter.Builder()
//                                        .setEndpoint(Retrofit.ROOT)  //call your base url
//                                        .build();
//                                Retrofit join = restAdapter.create(Retrofit.class); //this is how retrofit create your api
//                                join.join(info,new Callback<String>() {
//                                    @Override
//                                    public void success(String result, Response response) {
//                                        dialog.dismiss();
//                                        if(result.equals("Success"))
//                                            finish();
//                                        else
//                                        {
//                                            AlertDialog dialog2 = new AlertDialog.Builder(JoinActivity.this).setMessage("알수 없는 이유로 회원가입에 실패하였습니다.")
//                                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dia, int which) {
//                                                            dia.dismiss();
//                                                        }
//                                                    }).show();
//                                            TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
//                                            Typeface face=Typeface.SANS_SERIF;
//                                            textView.setTypeface(face);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void failure(RetrofitError retrofitError) {
//                                        dialog.dismiss();
//                                        AlertDialog dialog2 = new AlertDialog.Builder(JoinActivity.this).setMessage("네트워크 상태를 확인해주세요.")
//                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dia, int which) {
//                                                        dia.dismiss();
//                                                    }
//                                                }).show();
//                                        TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
//                                        Typeface face=Typeface.SANS_SERIF;
//                                        textView.setTypeface(face);
//                                    }
//                                });
//                            }
//                            catch (Throwable ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    }).start();
//                }
            }
        });

        CheckBox checkbox = (CheckBox) findViewById(R.id.seller_check);
        final LinearLayout Layout = (LinearLayout) findViewById(R.id.seller_form);

        Layout.setVisibility(View.GONE);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if (isChecked)
                    Layout.setVisibility(View.VISIBLE);
                else
                    Layout.setVisibility(View.GONE);
            }
        });


    }

    public void onClickedButton(View v){
        setContentView(R.layout.activity_join);
        CheckBox checkbox = (CheckBox) findViewById(R.id.seller_check);
        final LinearLayout Layout = (LinearLayout) findViewById(R.id.seller_form);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if (isChecked)
                    Layout.setVisibility(View.VISIBLE);
                else
                    Layout.setVisibility(View.GONE);
            }
        });

    }

    private TextWatcher MatchTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            EditText edit_pw = (EditText)findViewById(R.id.edit_join_pw);
            EditText edit_pwconfirm = (EditText)findViewById(R.id.edit_join_pwconfirm);
            TextView text=(TextView)(JoinActivity.this.findViewById(R.id.passwdconfirmtext));
            if(s.length()==0)
            {
                text.setText("");
            }
            else{
                if(edit_pw.getText().toString().equals(s.toString()))
                {
                    text.setText("일치합니다.");
                    text.setTextColor(Color.parseColor("#4CAF50"));
                    passwdmatch=true;
                }
                else
                {
                    text.setText("불일치합니다.");
                    text.setTextColor(Color.parseColor("#F44336"));
                    passwdmatch=false;
                }
            }
        }

    };
    private TextWatcher ValidtyTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            TextView text=(TextView)(JoinActivity.this.findViewById(R.id.passwdtext));
            if(s.length()==0)
            {
                text.setText("");
            }
            else{
                if(s.length()>5&&s.toString().matches(regExpStr))
                {
                    text.setText("사용가능한 비밀번호 입니다");
                    text.setTextColor(Color.parseColor("#4CAF50"));
                    passwdvalid=true;
                }
                else
                {
                    text.setText("사용 불가능한 비밀번호 입니다");
                    text.setTextColor(Color.parseColor("#F44336"));
                    passwdvalid=false;
                }
            }
        }

    };
}
