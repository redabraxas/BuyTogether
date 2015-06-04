package com.chocoroll.buyto.Mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocoroll.buyto.Extra.DownloadImageTask;
import com.chocoroll.buyto.R;

import java.util.ArrayList;

/**
 * Created by RA on 2015-05-23.*/
public class DealStateAdapter extends ArrayAdapter<DealState> {
    private ArrayList<DealState> items;
    private Context context;

    public DealStateAdapter(Context context, int textViewResourceId, ArrayList<DealState> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_deal_state, null);
        }
        DealState p = items.get(position);
        if (p != null) {

            new DownloadImageTask((ImageView) v.findViewById(R.id.dealLogo))
                    .execute(p.getThumbnail());

            String str = "["+p.getbCategory()+"/"+p.getsCategory()+"]";
            ((TextView)v.findViewById(R.id.txt_category)).setText(str);
            ((TextView)v.findViewById(R.id.txt_name)).setText(p.getDealName());
            ((TextView)  v.findViewById(R.id.statePrice)).setText(p.getPrice());

            ((TextView)  v.findViewById(R.id.buySure)).setVisibility(View.INVISIBLE);

            int stateNum = Integer.valueOf(p.getStateNum());
            if(stateNum ==0){
                ((TextView)  v.findViewById(R.id.stateNum)).setText(p.getWaiting());

            }else{
                ((TextView) v.findViewById(R.id.stateDate)).setText(p.getDate());
                ((TextView)  v.findViewById(R.id.stateDeposit)).setText(p.getDeposit());

                switch (stateNum){
                    case 1:
                        ((TextView)  v.findViewById(R.id.stateString)).setText("입금요망");
                        break;
                    case 2:
                        ((TextView)  v.findViewById(R.id.stateString)).setText("배송중");
                        ((TextView)  v.findViewById(R.id.buySure)).setVisibility(View.VISIBLE);
                        break;
                }
            }

        }
        return v;
    }
}
