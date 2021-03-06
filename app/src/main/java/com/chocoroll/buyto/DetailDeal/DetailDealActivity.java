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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.buyto.Extra.DownloadImageTask;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailDealActivity extends FragmentActivity{

    ProgressDialog dialog;
    static private Deal product;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    SlidingDrawer slidingDrawer;
    static Button slideHandleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deal);


        // 기본 정보 셋팅
        product = getIntent().getParcelableExtra("product");
        String str = "> " + product.getbCategory() + " / " + product.getsCategory() ;
        ((TextView) findViewById(R.id.txt_category)).setText(str);
        ((TextView) findViewById(R.id.txt_name)).setText(product.getName());
        ((TextView) findViewById(R.id.txt_dday)).setText(product.getDday());
        ((TextView) findViewById(R.id.txt_people)).setText(String.valueOf(product.getBook() + "/" + product.getMaxBook()));

        ((TextView)findViewById(R.id.price)).setText(product.getPrice()+" 원");



        new DownloadImageTask((ImageView) findViewById(R.id.dealLogo))
                .execute(product.getThumbnail());

        if(product.getLevel().equals("seller")){
            ( findViewById(R.id.level_bar)).setBackgroundColor(getResources().getColor(R.color.seller));
            ((TextView)findViewById(R.id.level_bar)).setText("SELLER");
        }else{
            ( findViewById(R.id.level_bar)).setBackgroundColor(getResources().getColor(R.color.user));
            ((TextView)findViewById(R.id.level_bar)).setText("USER");
        }

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //tabs.setTextColor(Color.WHITE);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setOffscreenPageLimit(3);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);




        // 슬라이딩 드로워 연결
        slideHandleButton = (Button) findViewById(R.id.slideHandleButton);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
        slidingDrawer.bringToFront();

        new DownloadImageTask((ImageView) findViewById(R.id.detailDealImage))
                .execute(product.getDetailView());



        // 찜 버튼
        TextView btnKeep = (TextView) findViewById(R.id.btn_keep);
        btnKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((MainActivity)MainActivity.mContext).getLoginmode() ==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
                    builder.setTitle("접근 불가")        // 제목 설정
                            .setMessage("로그인한 유저만 이용이 가능합니다.")        // 메세지 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                // 확인 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            });

                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }else{
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

            }
        });


        // 신청 버튼
        TextView btnBook = (TextView) findViewById(R.id.btn_book);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((MainActivity)MainActivity.mContext).getLoginmode() ==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailDealActivity.this);
                    builder.setTitle("접근 불가")        // 제목 설정
                            .setMessage("로그인한 유저만 이용이 가능합니다.")        // 메세지 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                // 확인 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            });

                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }else {
                    if (product.getState().equals(3)) {
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
                    } else {
                        BookDialog bookDialog = new BookDialog(DetailDealActivity.this, product.getNum(), ((MainActivity) MainActivity.mContext).getUserId());
                        bookDialog.show();
                    }
                }
            }
        });


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
                    return new DealQnaFragemnt(product.getSeller(),product.getNum());
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
            ((TextView)v.findViewById(R.id.seller_comment)).setText(product.getComment());

            TextView btn = (TextView)v.findViewById(R.id.detailbtn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    slideHandleButton.performClick();

                }
            });



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

            ((TextView)v.findViewById(R.id.sellerSite)).setText(product.getSite());
            ((TextView)v.findViewById(R.id.sellerTel)).setText(product.getPhone());

            if(product.getLevel().equals("seller")){

                getSellerInfo();

            }

            return v;
        }

        void getSellerInfo(){
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("사업자 정보를 가져오는 중입니다...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

            final JsonObject info = new JsonObject();
            info.addProperty("id", product.getSeller());

            new Thread(new Runnable() {
                public void run() {
                    try {

                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(Retrofit.ROOT)  //call your base url
                                .build();
                        Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                        retrofit.getSellerInfo(info, new Callback<JsonElement>() {

                            @Override
                            public void success(JsonElement element, Response response) {

                                dialog.dismiss();

                                JsonObject jsonObject = element.getAsJsonObject();
                                String result = (jsonObject.get("result")).getAsString();

                                if (result.equals("success")) {

                                    String sellerNum =   (jsonObject.get("sellerNum")).getAsString();
                                    String sellerComName =   (jsonObject.get("sellerComName")).getAsString();
                                    String sellerOffice =   (jsonObject.get("sellerOffice")).getAsString();

                                    TableLayout seller_box = (TableLayout)getView().findViewById(R.id.seller_box);

                                    seller_box.setVisibility(View.VISIBLE);
                                    ((TextView)getView().findViewById(R.id.sellerNum)).setText(sellerNum);
                                    ((TextView)getView().findViewById(R.id.sellerComName)).setText(sellerComName);
                                    ((TextView)getView().findViewById(R.id.sellerAddr)).setText(sellerOffice);

                                } else if (result.equals("failed")) {
                                    Toast.makeText(getActivity(), "사업자 정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();

                                }

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

                            Log.e("result: ",result.toString());
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



    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {

        // 슬라이딩 드로워가 열려있을때는 닫고, 홈이 아닐때는 홈으로 이동한다.
        if (slidingDrawer.isOpened()) {
            slidingDrawer.close ();
        }else{
            super.onBackPressed();
        }


    }

}
