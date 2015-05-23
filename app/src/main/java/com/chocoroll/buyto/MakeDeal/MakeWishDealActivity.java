package com.chocoroll.buyto.MakeDeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.R;
import com.chocoroll.buyto.Extra.Retrofit;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MakeWishDealActivity extends Activity implements OnClickListener {

    private Button uploadButton, btnselectpic, cancelButton;
    private ImageView imageview;
    private ProgressDialog ldialog = null;

    private AlertDialog dialog2 = null;

    private String imagepath = null;

    private String B_category = null;
    private String S_category = null;
    private Spinner spinnerS = null;
    private Spinner spinnerB = null;

    private String user_email=null;



    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_wishdeal);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        btnselectpic = (Button) findViewById(R.id.button_selectpic);
        imageview = (ImageView) findViewById(R.id.imageView_pic);
        cancelButton = (Button) findViewById(R.id.report_cancelbt);
        user_email =
                ((MainActivity)MainActivity.mContext).getUserId();

        btnselectpic.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);


        spinnerS = (Spinner)findViewById(R.id.spinner_small_category);
        spinnerB = (Spinner)findViewById(R.id.spinner_big_category);



        ArrayAdapter<CharSequence> adapter1 = null;
        adapter1= ArrayAdapter.createFromResource(MakeWishDealActivity.this, R.array.big_category_arrays,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerB.setAdapter(adapter1);

        spinnerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayAdapter<CharSequence> adapter = null;
                String item = spinnerB.getSelectedItem().toString();

                if (item.equals("패션/잡화")) {
                    adapter= ArrayAdapter.createFromResource(MakeWishDealActivity.this, R.array.small_category_arrays_fashion,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("뷰티")){
                    adapter= ArrayAdapter.createFromResource(MakeWishDealActivity.this, R.array.small_category_arrays_beauty,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("식품")){
                    adapter= ArrayAdapter.createFromResource(MakeWishDealActivity.this, R.array.small_category_arrays_food,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("주방/생활용품")){
                    adapter= ArrayAdapter.createFromResource(MakeWishDealActivity.this, R.array.small_category_arrays_living,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("가구/홈데코")){
                    adapter= ArrayAdapter.createFromResource(MakeWishDealActivity.this, R.array.small_category_arrays_furniture,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("가전/디지털")){
                    adapter= ArrayAdapter.createFromResource(MakeWishDealActivity.this, R.array.small_category_arrays_digital,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else{
                    // 전체보기인 경우

                }

                B_category = spinnerB.getSelectedItem().toString();

                spinnerS.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                S_category = spinnerS.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void onClick(View arg0) {


        if (arg0 == btnselectpic) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
        }
        else if( arg0 == cancelButton){
            finish();
        }
        else if (arg0 == uploadButton) {

            if(imagepath==null||
                    B_category==null||S_category==null||
                    ((EditText)findViewById(R.id.pro_name)).getText().toString()==null
                    ) //null check (상품코멘트는 생략가능)
            {
                dialog2 = new AlertDialog.Builder(MakeWishDealActivity.this).setMessage("양식을 모두 입력해주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).show();
                TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                Typeface face=Typeface.SANS_SERIF;
                textView.setTypeface(face);
            }

            else if(((EditText)findViewById(R.id.pro_name)).getText().toString().length()>20){
                dialog2 = new AlertDialog.Builder(MakeWishDealActivity.this).setMessage("상품 이름은 20자 미만이여야 합니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).show();
                TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                Typeface face=Typeface.SANS_SERIF;
                textView.setTypeface(face);

            }

            else{
                ldialog = ProgressDialog.show(MakeWishDealActivity.this, "", "Uploading file...", true);
                new Thread(new Runnable() {
                    public void run() {


                        JsonObject Uploadinfo = new JsonObject();


                        Uploadinfo.addProperty("pro_name",((EditText)findViewById(R.id.pro_name)).getText().toString());
                        Uploadinfo.addProperty("big_category",B_category);
                        Uploadinfo.addProperty("small_category",S_category);
                        Uploadinfo.addProperty("comment",((EditText)findViewById(R.id.pro_comment)).getText().toString());
                        Uploadinfo.addProperty("user_email",user_email);


                        Bitmap thumbnail;
                        BitmapFactory.Options option=new BitmapFactory.Options();
                        option.inSampleSize=2;
                        thumbnail=BitmapFactory.decodeFile(imagepath,option);
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] bytearray=stream.toByteArray();
                        String s= Base64.encodeToString(bytearray, Base64.DEFAULT);

                        thumbnail.recycle();
                        thumbnail=null;


                        Uploadinfo.addProperty("thumbnail",s);



                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(Retrofit.ROOT)  //call your base url
                                .build();
                        Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                        sendreport.UploadWishDeal(Uploadinfo,new Callback<String>() {
                            @Override
                            public void success(String result, Response response) {
                                ldialog.dismiss();
                                AlertDialog dialog = new AlertDialog.Builder(MakeWishDealActivity.this).setMessage(result)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dia, int which) {
                                                dia.dismiss();
                                                finish();
                                            }
                                        }).show();

                            }
                            @Override
                            public void failure(RetrofitError retrofitError) {
                                ldialog.dismiss();
                                AlertDialog dialog = new AlertDialog.Builder(MakeWishDealActivity.this).setMessage("네트워크 문제로 등록에 실패하였습니다.\n잠시후 다시시도해주세요.")
                                        .setPositiveButton("실패", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dia, int which) {
                                                dia.dismiss();
                                            }
                                        }).show();
                                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                                Typeface face=Typeface.SANS_SERIF;
                                textView.setTypeface(face);
                            }
                        });
                    }
                }).start();
            }}

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath();

            Uri selectedImageUri = data.getData();
            imagepath = getPath(selectedImageUri);

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;

            Bitmap bitmap = BitmapFactory.decodeFile(imagepath,options);
            imageview.setImageBitmap(bitmap);



        }


    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if ( ldialog != null)
            ldialog.dismiss();
    }
}
