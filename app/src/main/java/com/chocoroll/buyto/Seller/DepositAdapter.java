package com.chocoroll.buyto.Seller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.Mine.DealStateFragment;
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
public class DepositAdapter extends ArrayAdapter<Deposit> {


    private ArrayList<Deposit> items;
    private Context context;

    public DepositAdapter(Context context, int textViewResourceId, ArrayList<Deposit> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_deposit, null);
        }
        final Deposit p = items.get(position);
        if (p != null) {

            ((TextView)  v.findViewById(R.id.txtName)).setText(p.getName());

            TextView btnDeposit = (TextView)v.findViewById(R.id.btnDeposit);
            switch (p.getState()){
                case 1:
                    btnDeposit.setText("입금 확인");
                    btnDeposit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendDepositCheck(p.getDealNum(), p.getId());

                        }
                    });
                    break;
                case 2:
                    btnDeposit.setText("배송 확인");
                    btnDeposit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sellerDeliveryOK(p.getDealNum(), p.getId());
                        }
                    });
                    break;
                case 3:
                    btnDeposit.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    btnDeposit.setText("삭제");
                    btnDeposit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sellerDelete(p.getDealNum(), p.getId());
                        }
                    });
                    break;
            }

            ((TextView)  v.findViewById(R.id.txtPhone)).setText(p.getPhone());
            ((TextView)  v.findViewById(R.id.txtAddress)).setText(p.getAddress());

        }
        return v;
    }


    void sendDepositCheck(final String dealNum, String id){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("입금 리스트를 가져오는 중입니다...");
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
                    retrofit.sendDepositCheck(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();


                            AlertDialog.Builder builder = new AlertDialog.Builder(context);




                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("입금 확인이 완료되었습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {


                                                ((MainActivity)MainActivity.mContext).removeAllStack();
                                                FragmentTransaction ft = ((MainActivity)MainActivity.mContext).getSupportFragmentManager().beginTransaction();
                                                ft.replace(R.id.container, new SellerFragment());
                                                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                                                ft.addToBackStack(null);
                                                ft.commit();

                                                DepositFragment depositFragment = DepositFragment.newInstance(dealNum);
                                                ((MainActivity)MainActivity.mContext).getSupportFragmentManager().beginTransaction().add(R.id.container, depositFragment).addToBackStack(null).commit();


                                            }
                                        });
                            }else{
                                builder.setTitle("입금 확인 실패")        // 제목 설정
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



    void sellerDeliveryOK(final String dealNum, String id){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("배송 확인을 하는 중입니다...");
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
                    retrofit.sellerDeliveryOK(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();


                            AlertDialog.Builder builder = new AlertDialog.Builder(context);


                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("배송 확인이 완료되었습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {


                                                ((MainActivity)MainActivity.mContext).removeAllStack();
                                                FragmentTransaction ft = ((MainActivity)MainActivity.mContext).getSupportFragmentManager().beginTransaction();
                                                ft.replace(R.id.container, new SellerFragment());
                                                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                                                ft.addToBackStack(null);
                                                ft.commit();

                                                DepositFragment depositFragment = DepositFragment.newInstance(dealNum);
                                                ((MainActivity)MainActivity.mContext).getSupportFragmentManager().beginTransaction().add(R.id.container, depositFragment).addToBackStack(null).commit();

                                            }
                                        });
                            }else{
                                builder.setTitle("배송 확인 실패")        // 제목 설정
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



    void sellerDelete(final String dealNum, String id){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("삭제를 하는 중입니다...");
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
                    retrofit.sellerDelete(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();


                            AlertDialog.Builder builder = new AlertDialog.Builder(context);


                            if(result.equals("success")){
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("삭제가 완료되었습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {


                                                ((MainActivity)MainActivity.mContext).removeAllStack();
                                                FragmentTransaction ft = ((MainActivity)MainActivity.mContext).getSupportFragmentManager().beginTransaction();
                                                ft.replace(R.id.container, new SellerFragment());
                                                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                                                ft.addToBackStack(null);
                                                ft.commit();

                                                DepositFragment depositFragment = DepositFragment.newInstance(dealNum);
                                                ((MainActivity)MainActivity.mContext).getSupportFragmentManager().beginTransaction().add(R.id.container, depositFragment).addToBackStack(null).commit();

                                            }
                                        });
                            }else{
                                builder.setTitle("삭제 실패")        // 제목 설정
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
