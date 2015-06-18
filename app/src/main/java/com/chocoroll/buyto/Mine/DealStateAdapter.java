package com.chocoroll.buyto.Mine;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
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
import com.chocoroll.buyto.Seller.DepositFragment;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-05-23.*/
public class DealStateAdapter extends ArrayAdapter<DealState> {
    private ArrayList<DealState> items;
    private Context context;

    public DealStateAdapter(Context context, int textViewResourceId, ArrayList<DealState> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_deal_state, null);
        }
        final DealState p = items.get(position);
        if (p != null) {

            new DownloadImageTask((ImageView) v.findViewById(R.id.dealLogo))
                    .execute(p.getThumbnail());

            String str = "["+p.getbCategory()+"/"+p.getsCategory()+"]";
            ((TextView)v.findViewById(R.id.txt_category)).setText(str);
            ((TextView)v.findViewById(R.id.txt_name)).setText(p.getDealName());
            ((TextView)  v.findViewById(R.id.statePrice)).setText(p.getPrice());

            ((TextView)  v.findViewById(R.id.buySure)).setVisibility(View.INVISIBLE);
            ((TextView)  v.findViewById(R.id.buySure)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendBuySure(p.getDealNum(), ((MainActivity)MainActivity.mContext).getUserId());

                }
            });

            int stateNum = Integer.valueOf(p.getStateNum());
            if(stateNum ==0){
                ((TextView)  v.findViewById(R.id.stateNum)).setText(p.getWaiting());

            }else{
                ((TextView) v.findViewById(R.id.stateDate)).setText(p.getDate());
                ((TextView)  v.findViewById(R.id.stateDeposit)).setText(p.getDeposit());

                switch (stateNum){
                    case 1:
                        ((TextView)  v.findViewById(R.id.stateString)).setText("입금요망");
                        break;
                    case 2:
                        ((TextView)  v.findViewById(R.id.stateString)).setText("입금확인");
                    case 3:
                        ((TextView)  v.findViewById(R.id.stateString)).setText("배송중");
                        ((TextView)  v.findViewById(R.id.buySure)).setVisibility(View.VISIBLE);
                        break;
                }
            }

        }
        return v;
    }


    void sendBuySure(String dealNum, String id){


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("구매 확정 하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("dealNum", dealNum);
        info.addProperty("id", id);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendBuySure(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);

                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("구매 확정 완료되었습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                ((MainActivity)MainActivity.mContext).removeAllStack();
                                                FragmentTransaction ft = ((MainActivity)MainActivity.mContext).getSupportFragmentManager().beginTransaction();
                                                ft.replace(R.id.container, new DealStateFragment());
                                                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                                                ft.addToBackStack(null);
                                                ft.commit();
                                            }
                                        });
                            }else{
                                builder.setTitle("구매 확정 실패")        // 제목 설정
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
                }
                catch (Throwable ex) {

                }
            }
        }).start();
    }
}
