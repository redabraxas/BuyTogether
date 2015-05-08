package com.chocoroll.buyto.Home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chocoroll.buyto.Model.Product;
import com.chocoroll.buyto.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ViewPager pager;
    private MyPagerAdapter adapter;

    ArrayList<Product> pTodayList, pDealList, pWishList, pDdayList;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_home, container, false);



        pTodayList = new ArrayList<Product>();
        pDealList =  new ArrayList<Product>();
        pWishList =  new ArrayList<Product>();
        pDdayList =  new ArrayList<Product>();

        pTodayList.add(new Product("망고스틴","식품","과일","D-3",2));
        pTodayList.add(new Product("망고스틴","식품","과일","D-3",2));

        pDealList.add(new Product("망고스틴","식품","과일","D-3",2));
        pDealList.add(new Product("망고스틴","식품","과일","D-3",2));
        pDealList.add(new Product("망고스틴","식품","과일","D-3",2));

        pWishList.add(new Product("망고스틴","식품","과일","D-3",2));
        pWishList.add(new Product("망고스틴","식품","과일","D-3",2));
        pWishList.add(new Product("망고스틴","식품","과일","D-3",2));

        pDdayList.add(new Product("망고스틴","식품","과일","D-3",2));
        pDdayList.add(new Product("망고스틴","식품","과일","D-3",2));

        pager = (ViewPager)v.findViewById(R.id.pager);

        adapter = new MyPagerAdapter(this.getChildFragmentManager());

        pager.setOffscreenPageLimit(4);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setAdapter(adapter);

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


    /**
     * PagerAdapter
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {


            switch (position){
                case 0:
                    return new PListFragment(pTodayList);
                case 1:
                    return new PListFragment(pDealList);
                case 2:
                    return new PListFragment(pWishList);
                case 3:
                    return new PListFragment(pDdayList);
                default:
                    return null;
            }


        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "TODAY";
                case 1:
                    return"BEST DEAL";
                case 2:
                    return"BEST WISH";
                case 3:
                    return "D-DAY";
                default:
                    return "";
            }

        }

    }

}


