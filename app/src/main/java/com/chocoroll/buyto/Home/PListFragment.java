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
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.Model.DealAdapter;
import com.chocoroll.buyto.R;

import java.util.ArrayList;

/**
 * Created by RA on 2015-04-24.
 */
public class PListFragment extends Fragment {


    String page, title;
    ArrayList<Deal> pList;
    DealAdapter mAdapter;

    public PListFragment(ArrayList<Deal> pList){
        this.pList =pList;
    }


    public PListFragment(){
        this.pList = new ArrayList<Deal>();
    }


    public static PListFragment newInstance(int page, String title) {
        PListFragment fragmentFirst = new PListFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }



    /*
    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getString("someInt", "0");
        title = getArguments().getString("someTitle");
    }
*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = (ListView) v.findViewById(R.id.plistView);
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




}
