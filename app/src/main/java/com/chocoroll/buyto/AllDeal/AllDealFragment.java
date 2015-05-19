package com.chocoroll.buyto.AllDeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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


    ProgressDialog dialog;

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

                ArrayAdapter<CharSequence> adapter = null;
                String item = spinnerB.getSelectedItem().toString();

                if (item.equals("패션/잡화")) {
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_fashion,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("뷰티")){
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_beauty,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("식품")){
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_food,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("주방/생활용품")){
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_living,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("가구/홈데코")){
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_furniture,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("가전/디지털")){
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_digital,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else{
                    // 전체보기인 경우

                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("딜 리스트를 받아오는 중입니다...");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                    getDealList("전체보기","전체보기");
                }


                spinnerS.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("딜 리스트를 받아오는 중입니다...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                String item = spinnerS.getSelectedItem().toString();
                getDealList(spinnerB.getSelectedItem().toString(),item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        pList = new ArrayList<Deal>();

        listView = (ListView) v.findViewById(R.id.productlistView);
        mAdapter= new DealAdapter(getActivity(), R.layout.model_deal, pList);

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

        final JsonObject info = new JsonObject();
        info.addProperty("bCategory",bCategory);
        info.addProperty("sCategory",sCategory);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getDealList(info,new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            dialog.dismiss();
                            pList.clear();

                            for(int i=0; i<jsonElements.size(); i++) {
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


                                pList.add(new Deal(num, name,price, bCategory, sCategory, dday, maxBook, keep,book, thumbnail, detailView,
                                        comment, seller, phone, state));

                            }

                            listView.setAdapter(mAdapter);
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();

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
