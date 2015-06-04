package com.chocoroll.buyto.DetailDeal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-05-10.
 */
public class AnswerDialog extends Dialog{

    ProgressDialog dialog;
    ArrayList<Answer> answerList= new ArrayList<Answer>();
    String dealNum;
    String qnaNum;
    String seller;
    String writer;
    Context context;

    ListView m_ListView;
    AnswerAdapter m_Adapter;


    public AnswerDialog(Context context, String dealNum, String qnaNum, String seller, String writer) {
        super(context);
        this.context = context;
        this.dealNum = dealNum;
         this.qnaNum = qnaNum;
        this.seller = seller;
        this.writer = writer;
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_answer_list);
        this.setTitle("답변보기");

        m_ListView=(ListView) findViewById(R.id.answer_list);
        m_Adapter=new AnswerAdapter(getContext(), R.layout.model_answer, answerList);



        // 답변 달기는 셀러 또는 질문 작성자만 할 수 있다.
        RelativeLayout answerbox = (RelativeLayout)findViewById(R.id.seller_answer_box);

        if(((MainActivity)MainActivity.mContext).getUserId().equals(seller) ||  ((MainActivity)MainActivity.mContext).getUserId().equals(writer)){

            TextView replyAnswer = (TextView) findViewById(R.id.answer_ok);
            replyAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String content = ((EditText) findViewById(R.id.answer_content_this)).getText().toString();
                    if(content.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                        ((EditText) findViewById(R.id.answer_content_this)).setText("");
                        sendAnswer(content);
                    }


                }
            });
        }else{
            answerbox.setVisibility(View.GONE);
        }


        // 답변리스트를 가져온다.
        getAnswerList();


    }


    public void getAnswerList(){

        answerList.clear();

        dialog = new ProgressDialog(context);
        dialog.setMessage("답변리스트를 받아오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("dealNum", dealNum);
        info.addProperty("qnaNum", qnaNum);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getAnswerList(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            dialog.dismiss();


                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject jsonObject = (JsonObject) jsonElements.get(i);
                                String num = (jsonObject.get("num")).getAsString();
                                String writer = (jsonObject.get("writer")).getAsString();
                                String date = (jsonObject.get("date")).getAsString();
                                String content = (jsonObject.get("content")).getAsString();

                                answerList.add(new Answer(num,writer,date,content));

                            }
                            Collections.reverse(answerList);
                            m_ListView.setAdapter(m_Adapter);


                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            Log.e("error", retrofitError.getCause().toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    void sendAnswer(String content){


        dialog = new ProgressDialog(context);
        dialog.setMessage("답변을 작성하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("writer", ((MainActivity)(MainActivity.mContext)).getUserId());
        info.addProperty("dealNum", dealNum);
        info.addProperty("qnaNum", qnaNum);
        info.addProperty("content", content);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendAnswer(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();

                            if(result.equals("success")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("답변 작성 성공")        // 제목 설정
                                        .setMessage("답변을 성공적으로 작성하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                getAnswerList();
                                            }
                                        });

                                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                                dialog.show();    // 알림창 띄우기
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("실패")        // 제목 설정
                                        .setMessage("답변을 작성하는 데 실패하였습니다. 다시 시도해주세요.")        // 메세지 설정
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                ((TextView)  v.findViewById(R.id.answer_writer)).setText(p.getWriter());
                ((TextView) v.findViewById(R.id.answer_content)).setText(p.getContent());

            }
            return v;
        }
    }

}
