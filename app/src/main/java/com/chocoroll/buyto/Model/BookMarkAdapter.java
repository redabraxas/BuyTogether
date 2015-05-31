package com.chocoroll.buyto.Model;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocoroll.buyto.Extra.DownloadImageTask;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-05-31.
 */
public class BookMarkAdapter extends ArrayAdapter<BookMark> {
    private ArrayList<BookMark> items;
    private Context context;
    private int textViewResourceId;

    public BookMarkAdapter(Context context, int textViewResourceId, ArrayList<BookMark> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.textViewResourceId =textViewResourceId;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(textViewResourceId, null);
        }
        final BookMark p = items.get(position);
        if (p != null) {

            ((TextView)v.findViewById(R.id.bCategory)).setText(p.getbCategory());
            ((TextView)  v.findViewById(R.id.sCategory)).setText(p.getsCategory());
            ( v.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteBookMark(p.getbCategory(), p.getsCategory());
                }
            });
        }
        return v;
    }


    void deleteBookMark(String bCategory, String sCategory){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("즐겨찾기를 삭제하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();


        final JsonObject info = new JsonObject();
        info.addProperty("id", ((MainActivity)MainActivity.mContext).getUserId());
        info.addProperty("bigCategory",bCategory);
        info.addProperty("smallCategory", sCategory);



        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.deleteBookMark(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();

                            if(result.equals("success")){
                                ((MainActivity)MainActivity.mContext).getBookMark();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("즐겨찾기 삭제를 실패했습니다.")        // 제목 설정
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