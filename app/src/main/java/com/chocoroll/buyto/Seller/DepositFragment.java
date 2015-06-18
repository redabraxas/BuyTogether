package com.chocoroll.buyto.Seller;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepositFragment extends Fragment{

    ArrayList<Deposit> nameList = new ArrayList<Deposit>();
    ArrayList<Deposit> checkList  = new ArrayList<Deposit>();
    ArrayList<Deposit> deliveryList  = new ArrayList<Deposit>();
    ArrayList<Deposit> confirmList  = new ArrayList<Deposit>();

    String dealNum;

    // TODO: Rename and change types and number of parameters
    public static DepositFragment newInstance(String dealNum) {
        DepositFragment fragment = new DepositFragment();
        Bundle args = new Bundle();
        args.putString("dealNum", dealNum);
        fragment.setArguments(args);
        return fragment;
    }


    public DepositFragment() {
        // Required empty public constructor
    }

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;


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
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new DeporitListFragemnt(nameList);
                case 1:
                    return new DeporitListFragemnt(checkList);
                case 2:
                    return new DeporitListFragemnt(deliveryList);
                case 3:
                    return new DeporitListFragemnt(confirmList);
            }

            return null;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "신청명단";
                case 1:
                    return "입금자";
                case 2:
                    return "배송중";
                case 3:
                    return "구입확정";
                default:
                    return "";
            }

        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_deposit, container, false);
        dealNum = getArguments().getString("dealNum");

       tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        tabs.setTextColor(Color.WHITE);
        pager = (ViewPager) v.findViewById(R.id.pager);


        getDepositList();

        return v;
    }


    void getDepositList(){

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("입금 리스트를 가져오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("dealNum",dealNum);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getDepositList(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            dialog.dismiss();

                            for(int i=0; i<jsonElements.size(); i++) {

                                //userID deposit limitDay address phoneNumber state
                                JsonObject item = (JsonObject) jsonElements.get(i);
                                String id = (item.get("userID")).getAsString();
                                String name = (item.get("name")).getAsString();
                                String phone =(item.get("phone")).getAsString();
                                String address =(item.get("address")).getAsString();
                                int state = (item.get("state")).getAsInt();

                                switch (state){
                                    case 1:
                                        nameList.add(new Deposit(dealNum, id, name, phone, address, state));
                                        break;
                                    case 2:
                                        checkList.add(new Deposit(dealNum, id, name, phone, address, state));
                                        break;
                                    case 3:
                                        deliveryList.add(new Deposit(dealNum, id, name, phone, address, state));
                                        break;
                                    case 4:
                                        confirmList.add(new Deposit(dealNum, id, name, phone, address, state));
                                        break;
                                }

                            }


                            adapter = new MyPagerAdapter(getChildFragmentManager());
                            pager.setOffscreenPageLimit(3);
                            final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
                                    .getDisplayMetrics());
                            pager.setPageMargin(pageMargin);
                            pager.setAdapter(adapter);
                            tabs.setViewPager(pager);

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
                                            getActivity().finish();
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




    // 각 리스트 Fragemnt

    static public class DeporitListFragemnt extends Fragment {

        ArrayList <Deposit> depositList = new ArrayList<Deposit>();

        public DeporitListFragemnt() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public DeporitListFragemnt(ArrayList <Deposit> depositList) {
            // Required empty public constructor
            this.depositList= depositList;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_list, container, false);

            ListView listView = (ListView) v.findViewById(R.id.plistView);
            DepositAdapter mAdapter= new DepositAdapter(getActivity(), R.layout.model_deposit, depositList);
            listView.setAdapter(mAdapter);


            return v;
        }

    }


}
