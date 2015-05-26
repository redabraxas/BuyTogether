package com.chocoroll.buyto.Home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chocoroll.buyto.DetailDeal.DetailDealActivity;
import com.chocoroll.buyto.DetailDeal.WishDealDialog;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.Model.DealAdapter;
import com.chocoroll.buyto.Model.WishDeal;
import com.chocoroll.buyto.Model.WishDealAdapter;
import com.chocoroll.buyto.R;

import java.util.ArrayList;

/**
 * Created by RA on 2015-04-24.
 */
public class PListFragment extends Fragment {


    ArrayList<Deal> pList;
    ArrayList<WishDeal> pWishList;
    DealAdapter mAdapter;
    WishDealAdapter mWishAdapter;


    public static PListFragment newInstanceWishDeal(ArrayList<WishDeal> wishDealList) {
        PListFragment fragmentFirst = new PListFragment();
        Bundle args = new Bundle();
        args.putString("case","WishDeal");
        args.putParcelableArrayList("WishDeal", wishDealList);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    public static PListFragment newInstanceDeal(ArrayList<Deal> dealList) {
        PListFragment fragmentFirst = new PListFragment();
        Bundle args = new Bundle();
        args.putString("case","Deal");
        args.putParcelableArrayList("Deal", dealList);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = (ListView) v.findViewById(R.id.plistView);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(3);

        if(getArguments().getString("case").equals("Deal")){
            pList = new ArrayList<Deal>();
            pList = getArguments().getParcelableArrayList("Deal");

            mAdapter= new DealAdapter(getActivity(), R.layout.model_deal, pList);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Deal item =(Deal)mAdapter.getItem(i);
                    Intent intent = new Intent(getActivity(), DetailDealActivity.class);
                    intent.putExtra("product",item);
                    startActivity(intent);
                }
            }) ;

            listView.setAdapter(mAdapter);

        }
        else if(getArguments().getString("case").equals("WishDeal")){
            pWishList = new ArrayList<WishDeal>();
            pWishList = getArguments().getParcelableArrayList("WishDeal");

            mWishAdapter= new WishDealAdapter(getActivity(), R.layout.model_wishdeal, pWishList);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    WishDeal wishdeal = (WishDeal)mWishAdapter.getItem(i);
                    WishDealDialog dialog = new WishDealDialog(getActivity(), wishdeal);
                    dialog.show();
                }
            }) ;


            listView.setAdapter(mWishAdapter);

        }





        return v;
    }




}
