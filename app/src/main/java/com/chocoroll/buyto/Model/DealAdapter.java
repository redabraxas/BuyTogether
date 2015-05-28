package com.chocoroll.buyto.Model;

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
 * Created by RA on 2015-04-24.
 */
public class DealAdapter extends ArrayAdapter<Deal> {
    private ArrayList<Deal> items;
    private Context context;

    public DealAdapter(Context context, int textViewResourceId, ArrayList<Deal> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_deal, null);
        }
        Deal p = items.get(position);
        if (p != null) {

            new DownloadImageTask((ImageView) v.findViewById(R.id.thumbnailDeal))
                    .execute(p.getThumbnail());

            String str = "["+p.getbCategory()+"/"+p.getsCategory()+"]";
            ((TextView)v.findViewById(R.id.price)).setText(p.getPrice()+" 원");
            ((TextView)  v.findViewById(R.id.txt_category)).setText(str);
            ((TextView) v.findViewById(R.id.txt_name)).setText(p.getName());
            ((TextView) v.findViewById(R.id.txt_dday)).setText(p.getDday());
            ((TextView)  v.findViewById(R.id.txt_people)).setText(String.valueOf(p.getBook()+"/"+p.getMaxBook()));
        }
        return v;
    }
}
