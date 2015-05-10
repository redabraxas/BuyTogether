package com.chocoroll.buyto.DetailDeal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.R;

import java.util.ArrayList;

/**
 * Created by RA on 2015-05-10.
 */
public class AnswerDialog extends Dialog{

    ArrayList<Answer> answerList;
    String qnaNum;
    String seller;
    String writer;
    Context context;

    public AnswerDialog(Context context, String qnaNum, String seller, String writer) {
        super(context);
        this.context = context;
         this.qnaNum = qnaNum;
        this.seller = seller;
        this.writer = writer;
}

    public void getAnswerList(String qnaNum){

        answerList = new ArrayList<Answer>();
        answerList.add((new Answer("23","2013-03-03","감사합니다")));
        answerList.add((new Answer("23","2013-19-20","도움이됫다니 다행이에요")));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_answer_list);
        this.setTitle("답변보기");

        // 답변리스트를 가져온다.
        getAnswerList(qnaNum);


        ListView m_ListView=(ListView) findViewById(R.id.answer_list);
        AnswerAdapter m_Adapter=new AnswerAdapter(getContext(), R.layout.model_answer, answerList);
        m_ListView.setAdapter(m_Adapter);


        // 답변 달기는 셀러만 답변할 수 있다.
        RelativeLayout answerbox = (RelativeLayout)findViewById(R.id.seller_answer_box);

        if(((MainActivity)MainActivity.mContext).getUserId().equals(seller) ||  ((MainActivity)MainActivity.mContext).getUserId().equals(writer)){

            Log.e("gggg", "yes");
            Button replyAnswer = (Button) findViewById(R.id.answer_ok);
            replyAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String content = ((EditText) findViewById(R.id.answer_content_this)).getText().toString();
                    //DealQnaFragemnt fragemnt = new DealQnaFragemnt();
                    //fragemnt.sendAnswer(content);
                }
            });
        }else{
            Log.e("gggg", "no");
            answerbox.setVisibility(View.GONE);
        }


    }



    public class AnswerAdapter extends ArrayAdapter<Answer> {
        private ArrayList<Answer> items;
        private Context context;

        public AnswerAdapter(Context context, int textViewResourceId, ArrayList<Answer> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.model_answer, null);
            }
            final Answer p = items.get(position);
            if (p != null) {
                ((TextView)  v.findViewById(R.id.answer_date)).setText(p.getDate());
                ((TextView) v.findViewById(R.id.answer_content)).setText(p.getContent());

            }
            return v;
        }
    }

}
