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
 * Created by RA on 2015-05-15.
 */
public class WishDealAdapter  extends ArrayAdapter<WishDeal> {
    private ArrayList<WishDeal> items;
    private Context context;

    public WishDealAdapter(Context context, int textViewResourceId, ArrayList<WishDeal> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_wishdeal, null);
        }
        WishDeal p = items.get(position);
        if (p != null) {

            new DownloadImageTask((ImageView) v.findViewById(R.id.wishDealThumbnail))
                    .execute(p.getThumbnail());

            String str = "["+p.getbCategory()+"/"+p.getsCategory()+"]";

            ((TextView)  v.findViewById(R.id.txt_category)).setText(str);
            ((TextView) v.findViewById(R.id.txt_name)).setText(p.getName());
            ((TextView)  v.findViewById(R.id.txt_wish)).setText(p.getWish());

        }
        return v;
    }

}