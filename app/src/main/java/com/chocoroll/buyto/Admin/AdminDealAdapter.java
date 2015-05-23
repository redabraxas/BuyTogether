package com.chocoroll.buyto.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocoroll.buyto.Extra.DownloadImageTask;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-05-23.
 */
public class AdminDealAdapter  extends ArrayAdapter<Deal> {
    private ArrayList<Deal> items;
    private Context context;

    public AdminDealAdapter(Context context, int textViewResourceId, ArrayList<Deal> items) {
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
        final Deal p = items.get(position);
        if (p != null) {


            // 나중에 판매자인지 아닌지 상태 바기


            new DownloadImageTask((ImageView) v.findViewById(R.id.dealLogo))
                    .execute(p.getThumbnail());

            String str = "["+p.getbCategory()+"/"+p.getsCategory()+"]  "+p.getName();
            ((TextView)  v.findViewById(R.id.dealName)).setText(str);

            ((Button) v.findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendDealOK(p.getNum());
                }
            });


            ((Button) v.findViewById(R.id.btnNO)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendDealNO(p.getNum());
                }
            });

        }
        return v;
    }

    void sendDealOK(String dealNum){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("딜을 승인하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("dealNum", dealNum);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendDealOK(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);


                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("딜을 승인하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        });

                            }else{
                                builder.setTitle("딜 승인을 실패하였습니다.")        // 제목 설정
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

    void sendDealNO(String dealNum){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("딜을 거절하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("dealNum", dealNum);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendDealNO(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);


                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("딜을 거절하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        });

                            }else{
                                builder.setTitle("딜 거절을 실패하였습니다.")        // 제목 설정
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
