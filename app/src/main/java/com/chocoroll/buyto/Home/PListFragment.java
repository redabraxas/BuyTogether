package com.chocoroll.buyto.Home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chocoroll.buyto.Model.Product;
import com.chocoroll.buyto.Model.ProductAdapter;
import com.chocoroll.buyto.R;

import java.util.ArrayList;

/**
 * Created by RA on 2015-04-24.
 */
public class PListFragment extends Fragment {


    String page, title;
    ArrayList<Product> pList;
    ProductAdapter mAdapter;

    public PListFragment(ArrayList<Product> pList){
        this.pList =pList;
    }


    public PListFragment(){
        this.pList = new ArrayList<Product>();
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
        mAdapter= new ProductAdapter(getActivity(), R.layout.model_product, pList);


        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(3);

        listView.setAdapter(mAdapter);

        return v;
    }




}
