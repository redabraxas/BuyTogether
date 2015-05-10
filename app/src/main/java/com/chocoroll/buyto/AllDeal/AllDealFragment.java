package com.chocoroll.buyto.AllDeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.chocoroll.buyto.DetailDeal.DetailDealActivity;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.Model.DealAdapter;
import com.chocoroll.buyto.R;
import com.chocoroll.buyto.Retrofit.Retrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AllDealFragment extends Fragment {


    ArrayList<Deal> pList;
    DealAdapter mAdapter;
    ListView listView;

    public interface AllDealListner{
        void changeTitle(String str);
    }
    public AllDealFragment() {
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
        View v = inflater.inflate(R.layout.fragment_all_deal, container, false);

        final Spinner spinnerS = (Spinner)v.findViewById(R.id.spinner_small_category);
        final Spinner spinnerB = (Spinner)v.findViewById(R.id.spinner_big_category);

        spinnerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayAdapter<CharSequence> adapter;
                String item = spinnerB.getSelectedItem().toString();
                if (item.equals("패션/잡화")) {

                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_fashion,
                            android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerS.setAdapter(adapter);
                }else{
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_beauty,
                            android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerS.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        getDealList("","");

        pList = new ArrayList<Deal>();
        pList.add(new Deal("망고스틴", "식품", "과일", "D-3", "2","10", "seller","1000"));

        listView = (ListView) v.findViewById(R.id.productlistView);
        mAdapter= new DealAdapter(getActivity(), R.layout.model_product, pList);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(3);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Deal item =(Deal)mAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), DetailDealActivity.class);
                intent.putExtra("product",item);
                startActivity(intent);
            }
        }) ;

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((AllDealListner)getActivity()).changeTitle(">  모든 딜 보기");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AllDealListner)getActivity()).changeTitle("");
    }

    void getDealList(String bCategory, String sCategory){


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    sendreport.getDealList(new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            for(int i=0; i<jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String name = (deal.get("proName")).getAsString();
                                String price = (deal.get("proPrice")).getAsString();
                                String dday = (deal.get("limitDate")).getAsString();

                                pList.add(new Deal(name, "식품", "과일", dday, "2", "10", "seller",price));
                                listView.setAdapter(mAdapter);

                            }
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
                }
                catch (Throwable ex) {

                }
            }
        }).start();


    }


}
