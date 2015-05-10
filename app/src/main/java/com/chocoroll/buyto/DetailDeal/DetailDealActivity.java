package com.chocoroll.buyto.DetailDeal;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.R;

public class DetailDealActivity extends FragmentActivity implements DealQnaFragemnt.QnaListner{

    private Deal product;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    Button replyAnswer;


    SlidingDrawer slidingDrawer;
    Button slideHandleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deal);

        // 기본 정보 셋팅
        product = getIntent().getParcelableExtra("product");
        String str = "["+product.getbCategory()+"/"+product.getsCategory()+"]  "+product.getName();
        ((TextView) findViewById(R.id.txt_name)).setText(str);
        ((TextView) findViewById(R.id.txt_dday)).setText(product.getDday());
        ((TextView) findViewById(R.id.txt_people)).setText(String.valueOf(product.getCurCount() + "/" + product.getMaxCount()));

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //tabs.setTextColor(Color.WHITE);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);


        // 찜 버튼
        Button btnKeep = (Button)findViewById(R.id.btn_keep);
        btnKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        // 신청 버튼
        Button btnBook = (Button)findViewById(R.id.btn_book);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        // 답변

        // 답변보기셋팅
        slideHandleButton = (Button) findViewById(R.id.slideHandleButton);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
        slidingDrawer.bringToFront();
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
            }
        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
            }
        });
        // 답변 달기는 셀러만 답변할 수 있다.
        LinearLayout answerbox = (LinearLayout)findViewById(R.id.seller_answer_box);

        if(((MainActivity)MainActivity.mContext).getUserId().equals(product.getSeller())){
            replyAnswer = (Button) findViewById(R.id.answer_ok);
            replyAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String content = ((EditText) findViewById(R.id.answer_content_this)).getText().toString();
                    //DealQnaFragemnt fragemnt = new DealQnaFragemnt();
                    //fragemnt.sendAnswer(content);
                }
            });
        }else{
            answerbox.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void clickSlidingDrawer() {
        slidingDrawer.performClick();
    }



    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {

        if (slidingDrawer.isOpened()) {
            slidingDrawer.close ();
        } else {
            super.onBackPressed();
        }
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
}
