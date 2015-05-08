package com.chocoroll.buyto.AllDeal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.chocoroll.buyto.R;


public class AllDealFragment extends Fragment {


    public interface AllDealListner{
        void changeTitle(String str);
    }
    public AllDealFragment() {
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
        View v = inflater.inflate(R.layout.fragment_all_deal, container, false);

        final Spinner spinnerS = (Spinner)v.findViewById(R.id.spinner_small_category);
        final Spinner spinnerB = (Spinner)v.findViewById(R.id.spinner_big_category);

        spinnerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayAdapter<CharSequence> adapter;
                String item = spinnerB.getSelectedItem().toString();
                if (item.equals("패션/잡화")) {

                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_fashion,
                            android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerS.setAdapter(adapter);
                }else{
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_beauty,
                            android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerS.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((AllDealListner)getActivity()).changeTitle(">  모든 딜 보기");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AllDealListner)getActivity()).changeTitle("");
    }


}
