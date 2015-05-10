package com.chocoroll.buyto.DetailDeal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.Model.Deal;
import com.chocoroll.buyto.Model.DealAdapter;
import com.chocoroll.buyto.R;

import java.util.ArrayList;

/**
 * Created by RA on 2015-05-10.
 */
public class DealQnaFragemnt extends Fragment {

    public interface QnaListner{
        public void clickSlidingDrawer();
    }

    ArrayList<Qna> qList;
    QnaAdapter mAdapter;
    String seller;
    String dealNum;


    public DealQnaFragemnt() {
    }

    @SuppressLint("ValidFragment")
    public DealQnaFragemnt(String seller, String dealNum) {
        // Required empty public constructor
        this.seller = "";
        this.dealNum = dealNum;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_deal_qna, container, false);

        // 큐엔에이 작성구문
        Button btnQna = (Button)v.findViewById(R.id.btn_qna);
        btnQna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = ((EditText) v.findViewById(R.id.edit_qna)).getText().toString();
                if(content.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("작성 실패")        // 제목 설정
                            .setMessage("내용을 입력해주세요~")        // 메세지 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                // 확인 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }else{
                    sendQna(content);
                }
            }
        });

        qList = new ArrayList<Qna>();
        qList.add(new Qna("12","wirter","1994-09-023","asdf"));
        qList.add(new Qna("12","wirter","1994-09-023","asdf"));
        Log.e("gggg","list add");
        // 리스트뷰 셋팅
        ListView listView = (ListView) v.findViewById(R.id.listViewQna);
        mAdapter= new QnaAdapter(getActivity(), R.layout.model_qna, qList);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(3);
        listView.setAdapter(mAdapter);




        return v;
    }

    public void setAnswerList(String qnaNum){

        ArrayList<Answer> answerlist = new ArrayList<Answer>();
        answerlist.add((new Answer("23","asdf","!234")));
        ListView m_ListView=(ListView) getActivity().findViewById(R.id.answer_list);
        AnswerAdapter m_Adapter=new AnswerAdapter(getActivity(), R.layout.model_answer, answerlist);
        m_ListView.setAdapter(m_Adapter);

        ((QnaListner)getActivity()).clickSlidingDrawer();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    void sendQna(String content){

        String id = ((MainActivity)MainActivity.mContext).getUserId();
        String num = dealNum;



    }

    void sendAnswer(String content){

    }


    public class QnaAdapter extends ArrayAdapter<Qna>{
        private ArrayList<Qna> items;
        private Context context;

        public QnaAdapter(Context context, int textViewResourceId, ArrayList<Qna> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
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
                ((Button)v.findViewById(R.id.qna_showAnswer)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       setAnswerList(p.getNum());
                    }
                });
            }
            return v;
        }
    }




    public class AnswerAdapter extends ArrayAdapter<Answer>{
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
                v = vi.inflate(R.layout.model_qna, null);
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
