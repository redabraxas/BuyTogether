package com.chocoroll.buyto.DetailDeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.chocoroll.buyto.R;

import java.util.ArrayList;

/**
 * Created by RA on 2015-05-23.
 */

public class QnaAdapter extends ArrayAdapter<Qna> {
    private ArrayList<Qna> items;
    private Context context;
    String dealNum;
    String seller;

    public QnaAdapter(Context context, int textViewResourceId, ArrayList<Qna> items, String dealNum, String seller) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
        this.dealNum = dealNum;
        this.seller = seller;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_qna, null);
        }
        final Qna p = items.get(position);
        if (p != null) {
            ((TextView)  v.findViewById(R.id.qna_id)).setText(p.getWriter());
            ((TextView) v.findViewById(R.id.qna_date)).setText(p.getDate());
            ((TextView)  v.findViewById(R.id.qna_content)).setText(p.getContent());
            ((Button)v.findViewById(R.id.qna_showAnswer)).setText("답변보기("+p.getAnswerCount()+")");
            ((Button)v.findViewById(R.id.qna_showAnswer)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AnswerDialog dialog = new AnswerDialog(context, dealNum, p.getNum(),seller, p.getWriter());
                    dialog.show();
                }
            });
        }
        return v;
    }
}


