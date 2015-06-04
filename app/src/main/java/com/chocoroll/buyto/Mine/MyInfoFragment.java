package com.chocoroll.buyto.Mine;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment{


    public MyInfoFragment() {
        // Required empty public constructor
    }

    Switch push;
    private ProgressDialog dialog;
    EditText edit_pw;
    EditText edit_pwconfirm;
    String passwd;
    String passwd_confirm;
    int check;
    private String regExpStr = "^([a-z]+[0-9]+[a-z0-9]*|[0-9]+[a-z]+[a-z0-9]*)$";
    Button upload;
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my_info, container, false);
        push = (Switch) v.findViewById(R.id.push);
        edit_pw = (EditText)v.findViewById(R.id.edit_pw);
        edit_pwconfirm = (EditText)v.findViewById(R.id.edit_pwconfirm);


        push.setChecked(((MainActivity)MainActivity.mContext).getPushalarm());


        upload = (Button)v.findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passwd = edit_pw.getText().toString();
                passwd_confirm = edit_pwconfirm.getText().toString();

                if(passwd.length()<5 || !passwd.matches(regExpStr)){
                    AlertDialog dialog2 = new AlertDialog.Builder(getActivity()).setMessage("비밀번호는 6자이상이고 영어/숫자 혼합이어야합니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    dia.dismiss();
                                }
                            }).show();
                    TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                    Typeface face=Typeface.SANS_SERIF;
                    textView.setTypeface(face);

                }else if(!passwd_confirm.equals(passwd)){
                    AlertDialog dialog2 = new AlertDialog.Builder(getActivity()).setMessage("비밀번호가 일치하지 않습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    dia.dismiss();
                                }
                            }).show();
                    TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                    Typeface face=Typeface.SANS_SERIF;
                    textView.setTypeface(face);

                }else{
                    changeMyPasswd();
                }

            }
        });




        // 비밀번호 변경!

        push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    check = 1;
                }
                else
                    check = 0;

                changeMyPush();

            }
        });
        return  v;
    }

    void changeMyPush(){

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("정보변경 요청중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info=new JsonObject();
        info.addProperty("pushalarm", check);

        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        Typeface face=Typeface.SANS_SERIF;
        textView.setTypeface(face);
        new Thread(new Runnable() {
            public void run() {

                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit pushalarm = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    pushalarm.pushalarm(info,new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            dialog.dismiss();

                            AlertDialog dialog2 = new AlertDialog.Builder(getActivity()).setMessage("비밀 번호 변경을 완료하였습니다.")
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


                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog dialog2 = new AlertDialog.Builder(getActivity()).setMessage("네트워크 상태를 확인해주세요.")
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
                    });
                }
                catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }


    void changeMyPasswd(){
        dialog = new ProgressDialog(this.getActivity());
        dialog.setMessage("정보변경 요청중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();


        final JsonObject info=new JsonObject();
        info.addProperty("id", ((MainActivity)MainActivity.mContext).getUserId());
        info.addProperty("passwd", passwd);


        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        Typeface face=Typeface.SANS_SERIF;
        textView.setTypeface(face);
        new Thread(new Runnable() {
            public void run() {

                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit ch_passwd = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    ch_passwd.ch_passwd(info,new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            dialog.dismiss();

                            if(result.equals("success")){
                                AlertDialog dialog2 = new AlertDialog.Builder(getActivity()).setMessage("비밀 번호 변경을 완료하였습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dia, int which) {
                                                dia.dismiss();
                                            }
                                        }).show();
                                TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                                Typeface face=Typeface.SANS_SERIF;
                                textView.setTypeface(face);

                            }else{
                                Toast.makeText(getActivity(),"다시 시도해주세요.",Toast.LENGTH_SHORT).show();
                            }

                        }


                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog dialog2 = new AlertDialog.Builder(getActivity()).setMessage("네트워크 상태를 확인해주세요.")
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
                    });
                }
                catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

}
