package com.chocoroll.buyto.DetailDeal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chocoroll.buyto.Extra.DownloadImageTask;
import com.chocoroll.buyto.Extra.Retrofit;
import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.MakeDeal.MakeDealActivity;
import com.chocoroll.buyto.Model.WishDeal;
import com.chocoroll.buyto.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by HyeJi on 2015. 5. 27..
 */
public class WishDealDialog extends Dialog{

    ProgressDialog dialog;
    String dealNum;
    String name;
    String thumnail;
    String bCategory;
    String sCategory;
    String wishcount;
    String comment;

    Context context;


    public WishDealDialog(Context context,WishDeal pList) {
        super(context);
        this.context = context;
        this.dealNum = pList.getNum();
        this.name = pList.getName();
        this.thumnail = pList.getThumbnail();
        this.bCategory = pList.getbCategory();
        this.sCategory = pList.getsCategory();
        this.wishcount = pList.getWish();
        this.comment = pList.getComment();

        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_wish_deal);

        new DownloadImageTask((ImageView)findViewById(R.id.thumbnailDeal))
                .execute(thumnail);

        ((TextView)findViewById(R.id.big_category)).setText(bCategory);
        ((TextView)findViewById(R.id.small_category)).setText(sCategory);
        ((TextView)findViewById(R.id.product)).setText(name);
        ((TextView)findViewById(R.id.wish_count)).setText(wishcount);
        ((TextView)findViewById(R.id.comment)).setText(comment);

        ((ImageButton)findViewById(R.id.btnclose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });


        ((ImageButton)findViewById(R.id.btnwish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(((MainActivity)MainActivity.mContext).getUserId().equals("")){
                    Toast.makeText(context,"로그인 유저만 사용할 수 있습니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                JsonObject info=new JsonObject();
                info.addProperty("id",((MainActivity)MainActivity.mContext).getUserId());
                info.addProperty("wishNum", dealNum);


                dialog = new ProgressDialog(getContext());
                dialog.setMessage("Upload ..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                Wish(info);}
            }
        });

        ((TextView)findViewById(R.id.make_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

                if(((MainActivity)MainActivity.mContext).getUserId().equals("")){
                    Toast.makeText(context,"로그인 유저만 사용할 수 있습니다.",Toast.LENGTH_SHORT).show();
                }
                else{

                Intent intent = new Intent(context, MakeDealActivity.class);
                intent.putExtra("name",name);
                context.startActivity(intent);
                }
            }
        });


    }

    private void Wish(final JsonObject info){


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit Wish = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    Wish.Wish(info, new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            dialog.dismiss();
                            if(result.equals("success")) {

                                new AlertDialog.Builder(getContext()).setMessage("Wish Deal에 등록되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();
                                cancel();
                            }
                            else

                                {

    new AlertDialog.Builder(getContext()).setMessage("Wish Deal에 이미 등록되어 있습니다.")
            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            }).show();
    cancel();
}
                            }


                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("네트워크 에러")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요.")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            cancel();

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


}
