package com.chocoroll.buyto.DetailDeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.R;
import com.chocoroll.buyto.Retrofit.Retrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailDealActivity extends FragmentActivity implements DealQnaFragemnt.dealQnaListner{

    ProgressDialog dialog;
    private Deal product;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private ArrayList<Qna> qnaList = new ArrayList<Qna>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deal);


        // 기본 정보 셋팅
        product = getIntent().getParcelableExtra("product");
        String str = "[" + product.getbCategory() + "/" + product.getsCategory() + "]  " + product.getName();
        ((TextView) findViewById(R.id.txt_name)).setText(str);
        ((TextView) findViewById(R.id.txt_dday)).setText(product.getDday());
        ((TextView) findViewById(R.id.txt_people)).setText(String.valueOf(product.getBook() + "/" + product.getMaxBook()));

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //tabs.setTextColor(Color.WHITE);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setOffscreenPageLimit(3);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);


        // 찜 버튼
        Button btnKeep = (Button) findViewById(R.id.btn_keep);
        btnKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(product.getState().equals(3)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
                    builder.setTitle("이미 마감된 딜입니다.")        // 제목 설정
                            .setMessage("찜이 불가합니다.")        // 메세지 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                // 확인 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            });

                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }else{
                    sendKeepDeal();
                }
            }
        });


        // 신청 버튼
        Button btnBook = (Button) findViewById(R.id.btn_book);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(product.getState().equals(3)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
                    builder.setTitle("이미 마감된 딜입니다.")        // 제목 설정
                            .setMessage("신청이 불가합니다.")        // 메세지 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                // 확인 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            });

                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }else{
                    sendBookDeal();
                }

            }
        });

        getQnaList();

    }

    @Override
    public void addQnaList() {
        getQnaList();

    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ProductInfoFragemnt();
                case 1:
                    return new SellerInfoFragemnt();
                case 2:
                    return new DealQnaFragemnt(product.getSeller(),product.getNum(), qnaList);
            }

            return null;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "상품정보";
                case 1:
                    return "사업자정보";
                case 2:
                    return "질문&리뷰";
                default:
                    return "";
            }

        }
    }


    // 물건 정보 보여주는 프레그먼트

    static public class ProductInfoFragemnt extends Fragment {

        public ProductInfoFragemnt() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_product_detail, container, false);

            return v;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }
    }


    // 사업자 정보 보여주는 프레그먼트

    static public class SellerInfoFragemnt extends Fragment {

        public SellerInfoFragemnt() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_seller_detail, container, false);

            return v;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }
    }




    void getQnaList(){

        qnaList.clear();

        dialog = new ProgressDialog(DetailDealActivity.this);
        dialog.setMessage("딜 정보를 받아오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("pronum", product.getNum());

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getQnaList(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            dialog.dismiss();


                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("num")).getAsString();
                                String writer = (deal.get("writer")).getAsString();
                                String date = (deal.get("date")).getAsString();
                                String content = (deal.get("content")).getAsString();
                                String answerCount = (deal.get("answerCount")).getAsString();

                                qnaList.add(new Qna(num, writer, date, content, answerCount));

                            }


                            Collections.reverse(qnaList);
                            pager.setAdapter(adapter);
                            tabs.setViewPager(pager);

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
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


    void sendBookDeal(){
        dialog = new ProgressDialog(DetailDealActivity.this);
        dialog.setMessage("딜을 신청하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("id", ((MainActivity)(MainActivity.mContext)).getUserId());
        info.addProperty("dealNum", product.getNum());

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
                            Log.e("result: ",result);

                            if(result.equals("success")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
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

                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
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




    void sendKeepDeal(){
        dialog = new ProgressDialog(DetailDealActivity.this);
        dialog.setMessage("딜을 찜 하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("id", ((MainActivity)(MainActivity.mContext)).getUserId());
        info.addProperty("dealNum", product.getNum());

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendKeepDeal(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();

                            Log.e("result: ",result);
                            if(result.equals("success")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
                                builder.setTitle("성공")        // 제목 설정
                                        .setMessage("이 딜의 찜이 완료되었습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        });
                                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                                dialog.show();    // 알림창 띄우기

                            }else if(result.equals("failed")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
                                builder.setTitle("실패")        // 제목 설정
                                        .setMessage("이미 딜을 찜하셨습니다.")        // 메세지 설정
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
                            Log.e("error", retrofitError.getCause().toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
                            builder.setTitle("네트워크가 불안정합니다.")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            finish();
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
