package com.chocoroll.buyto.Home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.R;
import com.chocoroll.buyto.Retrofit.Retrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HomeFragment extends Fragment {

    private ViewPager pager;
    private MyPagerAdapter adapter;

    ArrayList<Deal> pTodayList, pDealList, pWishList, pDdayList;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_home, container, false);



        pTodayList = new ArrayList<Deal>();
        pDealList =  new ArrayList<Deal>();
        pWishList =  new ArrayList<Deal>();
        pDdayList =  new ArrayList<Deal>();

        pager = (ViewPager)v.findViewById(R.id.pager);

        adapter = new MyPagerAdapter(this.getChildFragmentManager());

        pager.setOffscreenPageLimit(4);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setAdapter(adapter);

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


    /**
     * PagerAdapter
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {


            switch (position){
                case 0:
                    return new PListFragment(pTodayList);
                case 1:
                    return new PListFragment(pDealList);
                case 2:
                    return new PListFragment(pWishList);
                case 3:
                    return new PListFragment(pDdayList);
                default:
                    return null;
            }


        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "TODAY";
                case 1:
                    return"BEST DEAL";
                case 2:
                    return"BEST WISH";
                case 3:
                    return "D-DAY";
                default:
                    return "";
            }

        }

    }



    void getOpenDateList(){

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getOpenDateList(new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {


                            for(int i=0; i<jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("proNum")).getAsString();
                                String name = (deal.get("proName")).getAsString();
                                String price = (deal.get("proPrice")).getAsString();

                                String bCategory = (deal.get("bicCategory")).getAsString();
                                String sCategory = (deal.get("smallCategory")).getAsString();

                                String dday = (deal.get("limitDate")).getAsString();
                                String maxBook = (deal.get("maxBook")).getAsString();

                                String keep = (deal.get("keepCount")).getAsString();
                                String book = (deal.get("bookCount")).getAsString();

                                String comment = (deal.get("proComment")).getAsString();

                                String seller = (deal.get("sellerID")).getAsString();
                                String phone = (deal.get("phone")).getAsString();

                                String state = (deal.get("state")).getAsString();


                                pTodayList.add(new Deal(num, name,price, bCategory, sCategory, dday, maxBook, keep,book, comment, seller, phone, state));

                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                            Log.e("error", retrofitError.getCause().toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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



    void getLimitDateList(){

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getLimitDateList(new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {


                            for(int i=0; i<jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("proNum")).getAsString();
                                String name = (deal.get("proName")).getAsString();
                                String price = (deal.get("proPrice")).getAsString();

                                String bCategory = (deal.get("bicCategory")).getAsString();
                                String sCategory = (deal.get("smallCategory")).getAsString();

                                String dday = (deal.get("limitDate")).getAsString();
                                String maxBook = (deal.get("maxBook")).getAsString();

                                String keep = (deal.get("keepCount")).getAsString();
                                String book = (deal.get("bookCount")).getAsString();

                                String comment = (deal.get("proComment")).getAsString();

                                String seller = (deal.get("sellerID")).getAsString();
                                String phone = (deal.get("phone")).getAsString();

                                String state = (deal.get("state")).getAsString();


                                pDdayList.add(new Deal(num, name,price, bCategory, sCategory, dday, maxBook, keep,book, comment, seller, phone, state));

                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                            Log.e("error", retrofitError.getCause().toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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




    void getBestWishList(){

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getBestWishList(new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {


                            for(int i=0; i<jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("proNum")).getAsString();
                                String name = (deal.get("proName")).getAsString();
                                String price = (deal.get("proPrice")).getAsString();

                                String bCategory = (deal.get("bicCategory")).getAsString();
                                String sCategory = (deal.get("smallCategory")).getAsString();

                                String dday = (deal.get("limitDate")).getAsString();
                                String maxBook = (deal.get("maxBook")).getAsString();

                                String keep = (deal.get("keepCount")).getAsString();
                                String book = (deal.get("bookCount")).getAsString();

                                String comment = (deal.get("proComment")).getAsString();

                                String seller = (deal.get("sellerID")).getAsString();
                                String phone = (deal.get("phone")).getAsString();

                                String state = (deal.get("state")).getAsString();

                                pWishList.add(new Deal(num, name,price, bCategory, sCategory, dday, maxBook, keep,book, comment, seller, phone, state));

                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                            Log.e("error", retrofitError.getCause().toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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



    void getBestDealList(){

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getBestDealList(new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {


                            for(int i=0; i<jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("proNum")).getAsString();
                                String name = (deal.get("proName")).getAsString();
                                String price = (deal.get("proPrice")).getAsString();

                                String bCategory = (deal.get("bicCategory")).getAsString();
                                String sCategory = (deal.get("smallCategory")).getAsString();

                                String dday = (deal.get("limitDate")).getAsString();
                                String maxBook = (deal.get("maxBook")).getAsString();

                                String keep = (deal.get("keepCount")).getAsString();
                                String book = (deal.get("bookCount")).getAsString();

                                String comment = (deal.get("proComment")).getAsString();

                                String seller = (deal.get("sellerID")).getAsString();
                                String phone = (deal.get("phone")).getAsString();

                                String state = (deal.get("state")).getAsString();


                                pDealList.add(new Deal(num, name,price, bCategory, sCategory, dday, maxBook, keep,book, comment, seller, phone, state));

                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                            Log.e("error", retrofitError.getCause().toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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


