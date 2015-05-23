package com.chocoroll.buyto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.chocoroll.buyto.Admin.AdminFragment;
import com.chocoroll.buyto.AllDeal.AllDealFragment;
import com.chocoroll.buyto.Login.JoinActivity;
import com.chocoroll.buyto.Login.LoginActivity;
import com.chocoroll.buyto.MakeDeal.MakeDealActivity;
import com.chocoroll.buyto.Mine.MyInfoFragment;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.AllDeal.WishDealFragment;
import com.chocoroll.buyto.Seller.SellerFragment;
import com.google.gson.JsonObject;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends FragmentActivity implements AllDealFragment.AllDealListner, WishDealFragment.WishDealListner{
    ProgressDialog dialog;

    // 안녕하세요~~~
    private SlidingMenu slidingMenu;
    TabHost tabs;
    TextView titleURL ;

    public static Context mContext;

    public static final int LOGOUTUSER = 0;
    public static final int USER = 1;
    public static final int SELLER = 2;
    public static final int ADMIN = 3;
    private String userid="";
    private int loginmode=0;

    public String getUserId(){
        return userid;
    }
    public int getLoginmode() { return loginmode; }
    public void setUserId(String id){
        userid= id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        mContext = this;


        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenuWidth);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenuOffset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setBehindOffset(200);


        ImageView left_btn = (ImageView) this.findViewById(R.id.left_menu);
        left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.showMenu(true);
            }
        });
        titleURL = (TextView)findViewById(R.id.title_url);



        // 자동로그인에 체크가 되어있따면
        SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
        if(setting.getBoolean("auto_login", false)){

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("로그인 정보를 받아오는 중입니다...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();



            userid = setting.getString("id", "");
            String passwd = setting.getString("pw","");
            JsonObject info = new JsonObject();
            info.addProperty("id", userid);
            info.addProperty("pw", passwd);
            Login(info);

        }else{
            menu_setting(LOGOUTUSER);
        }


    }
    private void Login(final JsonObject info){


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    sendreport.login(info, new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            dialog.dismiss();
                            if(result.equals("failed")){

                                new AlertDialog.Builder(MainActivity.this).setMessage("아이디가 변경되었으니 다시 로그인 해주세요")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                            }else if(result.equals("passwd_failed")){

                                new AlertDialog.Builder(MainActivity.this).setMessage("비밀번호가 변경되었으니 다시 로그인 해주세요.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                            }else{


                                if(result.equals("1"))
                                {
                                   menu_setting(MainActivity.USER);
                                }
                                else if(result.equals("2"))
                                {
                                   menu_setting(MainActivity.USER);
                                }
                                else if(result.equals("3"))
                                {
                                    menu_setting(MainActivity.SELLER);
                                }else if(result.equals("4")){
                                    menu_setting(MainActivity.ADMIN);
                                }
                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("네트워크 에러")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요.")        // 메세지 설정
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

    void make_menuTab(){



        tabs=(TabHost)findViewById(R.id.tabHost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("HOME");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("MENU");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("알람");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        for(int i=0; i<tabs.getTabWidget().getChildCount(); i++){
            tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.slide_tab_bar));
            ((TextView) tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title)).setTextColor(Color.parseColor("#ffffff"));
            tabs.getTabWidget().getChildAt(i).getLayoutParams().height = 80;
        }

        tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.slide_tab_backgroud));
        ((TextView) tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).findViewById(android.R.id.title)).setTextColor(Color.parseColor("#000000"));


        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                for(int i=0; i<tabs.getTabWidget().getChildCount(); i++){
                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.slide_tab_bar));
                    ((TextView) tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title)).setTextColor(Color.parseColor("#ffffff"));
                }
                tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.slide_tab_backgroud));
                ((TextView) tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).findViewById(android.R.id.title)).setTextColor(Color.parseColor("#000000"));
            }
        });

    }

    public void removeAllStack()
    {
        FragmentManager fm = getSupportFragmentManager();
        int cnt = fm.getBackStackEntryCount();
        for(int i = 0; i < cnt; ++i) 	{
            fm.popBackStack();
        }
    }

    public void menu_setting(int position){


        // 레이아웃 적용하기
        switch (position){
            case LOGOUTUSER:
                loginmode = LOGOUTUSER;
                slidingMenu.setMenu(R.layout.slide_menu_logoutuser);
                break;
            case USER:
                loginmode = USER;
                slidingMenu.setMenu(R.layout.slide_menu_user);
                break;
            case SELLER:
                loginmode = SELLER;
                slidingMenu.setMenu(R.layout.slide_menu_seller);
                break;
            case ADMIN:
                loginmode = ADMIN;
                slidingMenu.setMenu(R.layout.slide_menu_admin);
                break;

        }


        make_menuTab();


        // 공통 메뉴
        LinearLayout menu_all = (LinearLayout) findViewById(R.id.menu_all);
        LinearLayout menu_wish = (LinearLayout) findViewById(R.id.menu_wish);



        // 모든 딜 보기
        menu_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingMenu.showContent(true);

                removeAllStack();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, new AllDealFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        // 위시 딜 보기
        menu_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                slidingMenu.showContent(true);

                removeAllStack();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, new WishDealFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });



        if(position == LOGOUTUSER){
            LinearLayout menu_login = (LinearLayout) findViewById(R.id.menu_login);
            LinearLayout menu_regist = (LinearLayout) findViewById(R.id.menu_join);

            // 로그인
            menu_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slidingMenu.showContent(true);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            // 회원가입
            menu_regist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slidingMenu.showContent(true);
                    Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                    startActivity(intent);
                }
            });


        }else{
            ((TextView)findViewById(R.id.menu_hi)).setText("환영합니다, "+userid+"님!");

            LinearLayout menu_make = (LinearLayout) findViewById(R.id.menu_make);
            LinearLayout menu_bookmark = (LinearLayout) findViewById(R.id.menu_bookmark);
            LinearLayout menu_logout = (LinearLayout) findViewById(R.id.menu_logout);
            LinearLayout menu_myInfo = (LinearLayout) findViewById(R.id.menu_myinfo);

            TextView txtPush = (TextView) findViewById(R.id.textPush);
            // 딜 만들기
            menu_make.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slidingMenu.showContent(true);
                    Intent intent = new Intent(MainActivity.this, MakeDealActivity.class);
                    startActivity(intent);
                }
            });



            // 즐겨찾는 메뉴 설정
            menu_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ListView listView = (ListView) findViewById(R.id.listViewBookmark);
                    if(listView.getVisibility() == View.VISIBLE){
                        listView.setVisibility(View.GONE);
                    }else{
                        listView.setVisibility(View.VISIBLE);
                    }

                }
            });

            txtPush.setText("현재 도착한 알림이 없습니다.");

            // 로그아웃
            menu_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slidingMenu.showContent(true);
                    removeAllStack();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.commit();
                    userid="";
                    loginmode=MainActivity.LOGOUTUSER;

                    SharedPreferences setting1 = getSharedPreferences("setting", MODE_PRIVATE);
                    SharedPreferences.Editor editor = setting1.edit();

                    editor.remove("id");
                    editor.remove("loginmode");
                    editor.remove("auto_login");
                    editor.clear();
                    editor.commit();

                    menu_setting(LOGOUTUSER);

                }
            });


            // 내 정보 수정
            menu_myInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    slidingMenu.showContent(true);

                    removeAllStack();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new MyInfoFragment());
                    ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });


            if(position == SELLER){

                LinearLayout menu_seller = (LinearLayout) findViewById(R.id.menu_seller);
                menu_seller.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        slidingMenu.showContent(true);

                        removeAllStack();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new SellerFragment());
                        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                });

            }else  if(position == ADMIN){


                LinearLayout menu_amdin = (LinearLayout) findViewById(R.id.menu_amdin);
                menu_amdin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        slidingMenu.showContent(true);

                        removeAllStack();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new AdminFragment());
                        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                });

            }
        }


    }


    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && slidingMenu.isMenuShowing()) {
            slidingMenu.showContent(true);
            return true;
        }
        else if(keyCode == KeyEvent.KEYCODE_BACK&&!slidingMenu.isMenuShowing())
        {
            super.onKeyUp(keyCode, event);
        }
        return false;
    }


    @Override
    public void changeTitle(String str) {
        titleURL.setText(str);
    }
}
