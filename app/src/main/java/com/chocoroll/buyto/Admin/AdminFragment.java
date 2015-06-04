package com.chocoroll.buyto.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.Model.DealAdapter;
import com.chocoroll.buyto.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AdminFragment extends Fragment {


    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;


    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin, container, false);


        tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        //tabs.setTextColor(Color.WHITE);
        pager = (ViewPager) v.findViewById(R.id.pager);

        adapter = new MyPagerAdapter(getChildFragmentManager());
        pager.setOffscreenPageLimit(3);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
                return 2;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return AdminListFragemnt.newInstance("dealList");
                    case 1:
                        return AdminListFragemnt.newInstance("sellerList");
                }

                return null;
            }


            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "딜 리스트";
                    case 1:
                        return "판매자 명단";
                    default:
                        return "";
                }

            }
        }


    // 각 리스트 Fragemnt
    static public class AdminListFragemnt extends Fragment {

        String key;
        // 딜명단
        ArrayList<Deal> dealList = new ArrayList<Deal>();
        // 판매자 명단
        ArrayList<Seller> sellerList = new ArrayList<Seller>();

        DealAdapter mDealAdapter;
        SellerAdapter mSellerAdapter;
        ListView listView;
        public AdminListFragemnt() {
            // Required empty public constructor
        }


        // TODO: Rename and change types and number of parameters
        public static AdminListFragemnt newInstance(String key) {
            AdminListFragemnt fragment = new AdminListFragemnt();
            Bundle args = new Bundle();
            args.putString("case", key);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onAttach(Activity activity){
            super.onAttach(activity);
             key = getArguments().getString("case");

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_list, container, false);

            listView = (ListView) v.findViewById(R.id.plistView);




            if(key.equals("dealList")){
                mDealAdapter = new DealAdapter(getActivity(), R.layout.model_deal, dealList);
                getAdminDealList();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Deal item =(Deal)mDealAdapter.getItem(i);
                        PreDealDialog preDealDialog = new PreDealDialog(getActivity(), item);
                        preDealDialog.show();
                    }
                });

            }else{
                mSellerAdapter = new SellerAdapter(getActivity(), R.layout.model_seller, sellerList);
                getAdminSellerList();

            }



            return v;
        }

        void getAdminDealList() {

            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("딜 리스트를 가져오는 중입니다...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


            new Thread(new Runnable() {
                public void run() {
                    try {

                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(Retrofit.ROOT)  //call your base url
                                .build();
                        Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                        retrofit.getAdminDealList(new Callback<JsonArray>() {

                            @Override
                            public void success(JsonArray jsonElements, Response response) {

                                dialog.dismiss();


                                for (int i = 0; i < jsonElements.size(); i++) {
                                    JsonObject deal = (JsonObject) jsonElements.get(i);
                                    String num = (deal.get("proNum")).getAsString();
                                    String name = (deal.get("proName")).getAsString();
                                    String price = (deal.get("proPrice")).getAsString();

                                    String bCategory = (deal.get("bigCategory")).getAsString();
                                    String sCategory = (deal.get("smallCategory")).getAsString();

                                    String dday = (deal.get("limitDate")).getAsString();
                                    String maxBook = (deal.get("maxBook")).getAsString();

                                    String keep = (deal.get("keepCount")).getAsString();
                                    String book = (deal.get("bookCount")).getAsString();

                                    String thumbnail = (deal.get("thumbnail")).getAsString();
                                    String detailView = (deal.get("detailView")).getAsString();
                                    String comment = (deal.get("proComment")).getAsString();

                                    String seller = (deal.get("sellerID")).getAsString();
                                    String phone = (deal.get("phone")).getAsString();

                                    String state = (deal.get("state")).getAsString();

                                    String level = (deal.get("level")).getAsString();


                                    dealList.add(new Deal(num, name, price, bCategory, sCategory, dday, maxBook, keep, book, thumbnail, detailView,
                                            comment, seller, phone, state, level));
                                }

                                listView.setAdapter(mDealAdapter);
                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {

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
                    } catch (Throwable ex) {

                    }
                }
            }).start();


        }
        void getAdminSellerList(){

            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("셀러 리스트를 가져오는 중입니다...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


            new Thread(new Runnable() {
                public void run() {
                    try {

                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(Retrofit.ROOT)  //call your base url
                                .build();
                        Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                        retrofit.getAdminSellerList(new Callback<JsonArray>() {

                            @Override
                            public void success(JsonArray jsonElements, Response response) {

                                dialog.dismiss();


                                for (int i = 0; i < jsonElements.size(); i++) {
                                    JsonObject deal = (JsonObject) jsonElements.get(i);
                                    String id = (deal.get("userID")).getAsString();
                                    String sellerNum = (deal.get("sellerNum")).getAsString();
                                    String phone = (deal.get("sellerPhone")).getAsString();
                                    String address = (deal.get("sellerOffice")).getAsString();

                                    sellerList.add(new Seller(id,sellerNum,phone,address));
                                }

                                listView.setAdapter(mSellerAdapter);
                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {
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
                    } catch (Throwable ex) {

                    }
                }
            }).start();

        }
    }


}
