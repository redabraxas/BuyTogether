package com.chocoroll.buyto.DetailDeal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.R;
import com.chocoroll.buyto.Retrofit.Retrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-05-10.
 */
public class DealQnaFragemnt extends Fragment {

    public interface dealQnaListner{
        public void addQnaList();
    }

    ProgressDialog dialog;

    ArrayList<Qna> qnaList =  new ArrayList<Qna>();
    QnaAdapter mAdapter;

    String seller;
    String dealNum;


    public DealQnaFragemnt() {
    }

    @SuppressLint("ValidFragment")
    public DealQnaFragemnt(String seller, String dealNum, ArrayList<Qna> qnaList) {
        // Required empty public constructor
        this.seller = "asdf";
        this.dealNum = dealNum;
        this.qnaList = qnaList;
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

                if(((MainActivity)MainActivity.mContext).getLoginmode() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("작성 실패")        // 제목 설정
                            .setMessage("로그인한 유저만 작성 가능합니다.")        // 메세지 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                // 확인 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }else if(content.equals("")){
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
                    ((EditText) v.findViewById(R.id.edit_qna)).setText("");
                    sendQna(content);

                }
            }
        });

        // 리스트뷰 셋팅
        ListView listView = (ListView) v.findViewById(R.id.listViewQna);
        mAdapter= new QnaAdapter(getActivity(), R.layout.model_qna, qnaList);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(3);
        listView.setAdapter(mAdapter);




        return v;
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


        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("질문을 작성하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("writer", ((MainActivity)(MainActivity.mContext)).getUserId());
        info.addProperty("dealNum", dealNum);
        info.addProperty("content", content);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendQna(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();

                            if(result.equals("success")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("질문 작성 성공")        // 제목 설정
                                        .setMessage("질문을 성공적으로 작성하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                ((dealQnaListner)getActivity()).addQnaList();
                                            }
                                        });

                                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                                dialog.show();    // 알림창 띄우기
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("실패")        // 제목 설정
                                        .setMessage("질문을 작성하는 데 실패하였습니다. 다시 시도해주세요.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        });

                                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                                dialog.show();    // 알림창 띄우기
                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            Log.e("error", retrofitError.getCause().toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("네트워크가 불안정합니다.")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    });

                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기

                        }
                    });
                }
                catch (Throwable ex) {

                }
            }
        }).start();




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
                ((Button)v.findViewById(R.id.qna_showAnswer)).setText("답변보기("+p.getAnswerCount()+")");
                ((Button)v.findViewById(R.id.qna_showAnswer)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AnswerDialog dialog = new AnswerDialog(getActivity(), dealNum, p.getNum(),seller, p.getWriter());
                        dialog.show();
                    }
                });
            }
            return v;
        }
    }




}
