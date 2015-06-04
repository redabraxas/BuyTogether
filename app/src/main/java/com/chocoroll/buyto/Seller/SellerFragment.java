package com.chocoroll.buyto.Seller;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chocoroll.buyto.DetailDeal.DetailDealActivity;
import com.chocoroll.buyto.Extra.DownloadImageTask;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.R;
import com.chocoroll.buyto.Extra.Retrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SellerFragment extends Fragment {

    ArrayList<Deal> sellerDealList = new ArrayList<Deal>();
    SellerDealAdapter mAdapter;
    ListView listViewSeller;



    public SellerFragment() {
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
        View v=  inflater.inflate(R.layout.fragment_seller, container, false);

        listViewSeller = (ListView) v.findViewById(R.id.listViewSeller);
        mAdapter= new SellerDealAdapter(getActivity(), R.layout.model_seller_deal, sellerDealList);

        getSellerDealList();


        return v;
    }

    void getSellerDealList(){

        final ProgressDialog  dialog = new ProgressDialog(getActivity());
        dialog.setMessage("나의 판매 딜 리스트를 가져오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();


        final JsonObject info = new JsonObject();
        info.addProperty("id", ((MainActivity)MainActivity.mContext).getUserId());



        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getSellerDealList(info, new Callback<JsonArray>() {

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


                                sellerDealList.add(new Deal(num, name, price, bCategory, sCategory, dday, maxBook, keep, book, thumbnail, detailView,
                                        comment, seller, phone, state, level));
                            }

                            listViewSeller.setAdapter(mAdapter);
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



    public class SellerDealAdapter extends ArrayAdapter<Deal> {
        private ArrayList<Deal> items;
        private Context context;

        public SellerDealAdapter(Context context, int textViewResourceId, ArrayList<Deal> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.model_seller_deal, null);
            }
            final Deal p = items.get(position);
            if (p != null) {

                new DownloadImageTask((ImageView) v.findViewById(R.id.thumbnailDeal))
                        .execute(p.getThumbnail());

                String str = "["+p.getbCategory()+"/"+p.getsCategory()+"]  "+p.getName();
                ((TextView)  v.findViewById(R.id.txt_name)).setText(str);
                ((TextView) v.findViewById(R.id.txt_dday)).setText(p.getDday());
                ((TextView)  v.findViewById(R.id.txt_people)).setText(String.valueOf(p.getBook()+"/"+p.getMaxBook()));

                ((Button) v.findViewById(R.id.btnDetail)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailDealActivity.class);
                        intent.putExtra("product", p);
                        startActivity(intent);


                    }
                });

                ((Button) v.findViewById(R.id.btnDeposit)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DepositFragment depositFragment = DepositFragment.newInstance(p.getNum());
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, depositFragment).addToBackStack(null).commit();

                    }
                });
            }
            return v;
        }

    }


}
