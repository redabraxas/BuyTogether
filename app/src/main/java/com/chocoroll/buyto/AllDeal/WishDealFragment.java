package com.chocoroll.buyto.AllDeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chocoroll.buyto.DetailDeal.WishDealDialog;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.Model.WishDeal;
import com.chocoroll.buyto.Model.WishDealAdapter;
import com.chocoroll.buyto.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class WishDealFragment extends Fragment {

    ArrayList<WishDeal> pList;
    WishDealAdapter mAdapter;
    ListView listView;

    private Context context;


    public interface WishDealListner{
        void changeTitle(String str);
    }


    public WishDealFragment() {
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
        final View v = inflater.inflate(R.layout.fragment_wish_deal, container, false);

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
                }else if(item.equals("가전/디지털")) {
                    adapter = ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_digital,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("스포츠/레저용품")){
                    adapter = ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_sport,
                            android.R.layout.simple_spinner_item);
                }else if(item.equals("도서/문구/취미")){
                    adapter = ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_goods,
                            android.R.layout.simple_spinner_item);
                }else if(item.equals("애완용품")){
                    adapter = ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_pet,
                            android.R.layout.simple_spinner_item);
                }
                else{
                    // 전체보기인 경우
                    getWishDealList("전체보기", "전체보기", "");
                    ((EditText)v.findViewById(R.id.editSearch)).setText("");
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

                String item = spinnerS.getSelectedItem().toString();
                getWishDealList(spinnerB.getSelectedItem().toString(), item, "");
                ((EditText)v.findViewById(R.id.editSearch)).setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        (v.findViewById(R.id.btnSearch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str =((EditText)v.findViewById(R.id.editSearch)).getText().toString();

                if(str.equals("")){
                    Toast.makeText(getActivity(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{

                    String bCategory = spinnerB.getSelectedItem().toString();

                    if (bCategory.equals("전체보기")) {
                        getWishDealList("전체보기", "전체보기", str);
                    }else{
                        String sCategory = spinnerS.getSelectedItem().toString();
                        getWishDealList(bCategory, sCategory, str);
                    }




                }
            }
        });



        pList = new ArrayList<WishDeal>();

        listView = (ListView) v.findViewById(R.id.listViewWishDeal);
        mAdapter= new WishDealAdapter(getActivity(), R.layout.model_wishdeal, pList);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(3);

        listView.setAdapter(mAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                WishDeal wishdeal = (WishDeal)adapterView.getItemAtPosition(i);
                WishDealDialog dialog = new WishDealDialog(getActivity(), wishdeal);
                dialog.show();

            }
        }) ;
        getWishDealList("전체보기", "전체보기", "");

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((WishDealListner)getActivity()).changeTitle(">  위시 딜 보기");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((WishDealListner)getActivity()).changeTitle("");
    }

    void getWishDealList(String bCategory, String sCategory, String search){

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("위시 딜 리스트를 받아오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("bCategory",bCategory);
        info.addProperty("sCategory",sCategory);
        info.addProperty("search",search);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getWishDealList(info,new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            dialog.dismiss();
                            pList.clear();

                            for(int i=0; i<jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("wishNum")).getAsString();
                                String name = (deal.get("proName")).getAsString();

                                String bCategory = (deal.get("bigCategory")).getAsString();
                                String sCategory = (deal.get("smallCategory")).getAsString();

                                String wish = (deal.get("wishPeopleCount")).getAsString();

                                String comment = (deal.get("comment")).getAsString();
                                String thumbnail = (deal.get("thumbnail")).getAsString();


                                pList.add(new WishDeal(num, name, bCategory, sCategory, wish, thumbnail, comment));

                            }

                            Log.e("wish","NO ITEM");

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
