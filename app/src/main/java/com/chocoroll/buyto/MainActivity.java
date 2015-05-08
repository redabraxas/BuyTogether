package com.chocoroll.buyto;

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
import android.widget.TabHost;
import android.widget.TextView;

import com.chocoroll.buyto.AllDeal.AllDealFragment;
import com.chocoroll.buyto.WishDeal.WishDealFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class MainActivity extends FragmentActivity implements AllDealFragment.AllDealListner, WishDealFragment.WishDealListner{

    // 안녕하세요~~~
    private SlidingMenu slidingMenu;
    TabHost tabs;
    TextView titleURL ;

    public static final int LOGOUTUSER = 0;
    public static final int USER = 1;
    public static final int SELLER = 2;
    public static final int ADMIN = 3;
    private String userid;
    private int loginmode=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);



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

            userid = setting.getString("id","");
            loginmode = setting.getInt("loginmode",0);
            //setLoginmode();

        }else{
            menu_setting(ADMIN);
        }





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

    void menu_setting(int position){


        // 레이아웃 적용하기
        switch (position){
            case LOGOUTUSER:
                slidingMenu.setMenu(R.layout.slide_menu_logoutuser);
                break;
            case USER:
                slidingMenu.setMenu(R.layout.slide_menu_user);
                break;
            case SELLER:
                break;
            case ADMIN:
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
            LinearLayout menu_regist = (LinearLayout) findViewById(R.id.menu_regist);

            // 로그인
            menu_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            // 회원가입
            menu_regist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }else{
            LinearLayout menu_make = (LinearLayout) findViewById(R.id.menu_make);
            LinearLayout menu_bookmark = (LinearLayout) findViewById(R.id.menu_bookmark);
            TextView txtPush = (TextView) findViewById(R.id.textPush);
            // 딜 만들기
            menu_make.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });



            // 즐겨찾는 메뉴 설정
            menu_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            txtPush.setText("현재 도착한 알림이 없습니다.");



            if(position == SELLER){

            }else  if(position == ADMIN){


                LinearLayout menu_amdin = (LinearLayout) findViewById(R.id.menu_amdin);
                menu_amdin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
