package com.chocoroll.buyto.MakeDeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.chocoroll.buyto.MainActivity;
import com.chocoroll.buyto.R;
import com.chocoroll.buyto.Retrofit.Retrofit;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MakeDealActivity extends Activity implements OnClickListener {

    private Button uploadButton, btnselectpic, btnselectpic1,cancelButton;
    private ImageView imageview;
    private ImageView imageview1;
    private int serverResponseCode = 0;
    private ProgressDialog ldialog = null;

    private AlertDialog dialog2 = null;

    private String imagepath = null;
    private String imagepath1 = null;
    private String B_category = null;
    private String phone = null;
    private String S_category = null;
    private Spinner spinnerS = null;
    private Spinner spinnerB = null;
    private Spinner spinner_phone = null;
    private String bank = null;
    private String today = null;

    private Spinner spinner_bank = null;

    private TextView mDateDisplay;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;

    private String user_email=null;



    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_deal);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        btnselectpic = (Button) findViewById(R.id.button_selectpic);
        btnselectpic1 = (Button) findViewById(R.id.button_selectpic1);
        imageview = (ImageView) findViewById(R.id.imageView_pic);
        imageview1 = (ImageView) findViewById(R.id.imageView_pic1);
        cancelButton = (Button) findViewById(R.id.report_cancelbt);
         user_email =
              ((MainActivity)MainActivity.mContext).getUserId();

        btnselectpic.setOnClickListener(this);
        btnselectpic1.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        //// Date Picker : 시작 ////
        //(1) main.xml의 레이아수에 배치된 날짜 입력을 위한 TextView 인식
        mDateDisplay = (TextView) findViewById(R.id.edit_birthday);
        //(2) 인식된 TextView 에 click listener 추가
        mDateDisplay.setOnClickListener(new OnClickListener() {
            //(5) 클릭되면 실행
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth= c.get(Calendar.MONTH);
        mDay  = c.get(Calendar.DAY_OF_MONTH);
        today = mYear+"-"+(mMonth+1)+"-"+mDay;
        updateDisplay();


        spinnerS = (Spinner)findViewById(R.id.spinner_small_category);
        spinnerB = (Spinner)findViewById(R.id.spinner_big_category);
        spinner_phone =(Spinner) findViewById(R.id.spinner_phone);


        ArrayAdapter<CharSequence> Padapter = null;
        Padapter=ArrayAdapter.createFromResource(MakeDealActivity.this, R.array.number,
                android.R.layout.simple_spinner_item);
        Padapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_phone.setAdapter(Padapter);


        spinner_phone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                phone = spinner_phone.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_bank =(Spinner) findViewById(R.id.spinner_bank);


        ArrayAdapter<CharSequence> Aadapter = null;
        Aadapter=ArrayAdapter.createFromResource(MakeDealActivity.this, R.array.bank,
                android.R.layout.simple_spinner_item);
        Aadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_bank.setAdapter(Aadapter);


        spinner_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank = spinner_bank.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ArrayAdapter<CharSequence> adapter1 = null;
        adapter1= ArrayAdapter.createFromResource(MakeDealActivity.this, R.array.big_category_arrays,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerB.setAdapter(adapter1);

        spinnerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

           @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayAdapter<CharSequence> adapter = null;
                String item = spinnerB.getSelectedItem().toString();

                if (item.equals("패션/잡화")) {
                    adapter= ArrayAdapter.createFromResource(MakeDealActivity.this, R.array.small_category_arrays_fashion,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("뷰티")){
                    adapter= ArrayAdapter.createFromResource(MakeDealActivity.this, R.array.small_category_arrays_beauty,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("식품")){
                    adapter= ArrayAdapter.createFromResource(MakeDealActivity.this, R.array.small_category_arrays_food,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("주방/생활용품")){
                    adapter= ArrayAdapter.createFromResource(MakeDealActivity.this, R.array.small_category_arrays_living,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("가구/홈데코")){
                    adapter= ArrayAdapter.createFromResource(MakeDealActivity.this, R.array.small_category_arrays_furniture,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("가전/디지털")){
                    adapter= ArrayAdapter.createFromResource(MakeDealActivity.this, R.array.small_category_arrays_digital,
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
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case DATE_DIALOG_ID : return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            // 사용자가 지정한 날짜를 출력
            updateDisplay();

        }
    };
    private void updateDisplay() {
        // main.xml의 레이아웃에 배치된 날짜 입력 TextView에 인식된 날짜 출력
        mDateDisplay.setText(
                new StringBuilder()
                        //월은 시스템에서 0~11로 인식하기 때문에 1을 더해줌
                        .append(mYear).append("-")
                        .append(mMonth+1).append("-")
                        .append(mDay).append(" ")
        );

    }

    @Override
    public void onClick(View arg0) {


        if (arg0 == btnselectpic) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
        }
        else if (arg0 == btnselectpic1) {
            Intent intent1 = new Intent();
            intent1.setType("image/*");
            intent1.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent1, "Complete action using"), 2);
        }
        else if( arg0 == cancelButton){
            finish();
        }
        else if (arg0 == uploadButton) {

            if(imagepath==null||imagepath1==null
                    ||B_category==null||S_category==null||
                    ((EditText)findViewById(R.id.pro_name)).getText().toString()==null||
                    ((EditText)findViewById(R.id.pro_price)).getText().toString()==null||
                    ((EditText)findViewById(R.id.account)).getText().toString()==null||
                    ((EditText)findViewById(R.id.member)).getText().toString()==null||
                    ((EditText)findViewById(R.id.phone2)).getText().toString()==null||
                    ((EditText)findViewById(R.id.phone3)).getText().toString()==null
                    ) //null check (site와 상품코멘트는 생략가능)
            {
                dialog2 = new AlertDialog.Builder(MakeDealActivity.this).setMessage("양식을 모두 입력해주세요.")
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
                dialog2 = new AlertDialog.Builder(MakeDealActivity.this).setMessage("상품 이름은 20자 미만이여야 합니다.")
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
            ldialog = ProgressDialog.show(MakeDealActivity.this, "", "Uploading file...", true);
            new Thread(new Runnable() {
                public void run() {


                    JsonObject Uploadinfo = new JsonObject();


                    Uploadinfo.addProperty("date",((EditText)findViewById(R.id.edit_birthday)).getText().toString());
                    Uploadinfo.addProperty("pro_name",((EditText)findViewById(R.id.pro_name)).getText().toString());
                    Uploadinfo.addProperty("big_category",B_category);
                    Uploadinfo.addProperty("small_category",S_category);
                    Uploadinfo.addProperty("pro_comment",((EditText)findViewById(R.id.pro_comment)).getText().toString());
                    Uploadinfo.addProperty("pro_price",((EditText)findViewById(R.id.pro_price)).getText().toString());
                    Uploadinfo.addProperty("maxBook",((EditText)findViewById(R.id.member)).getText().toString());
                    Uploadinfo.addProperty("site",((EditText)findViewById(R.id.site)).getText().toString());
                    Uploadinfo.addProperty("phone_number",phone+"-"+((EditText)findViewById(R.id.phone2)).getText().toString()+"-"+((EditText)findViewById(R.id.phone3)).getText().toString());
                    Uploadinfo.addProperty("limit_date",((EditText)findViewById(R.id.edit_birthday)).getText().toString());
                    Uploadinfo.addProperty("open_date",today);
                    Uploadinfo.addProperty("account",bank+" "+((EditText)findViewById(R.id.account)).getText().toString());
                    Uploadinfo.addProperty("seller_id",user_email);


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


                    Bitmap detailView;
                    option=new BitmapFactory.Options();
                    option.inSampleSize=2;
                    detailView=BitmapFactory.decodeFile(imagepath1,option);
                    stream=new ByteArrayOutputStream();
                    detailView.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    bytearray=stream.toByteArray();
                    s= Base64.encodeToString(bytearray, Base64.DEFAULT);

                    detailView.recycle();
                    detailView=null;

                    if(TextUtils.isEmpty(s)){


                    }


                    Uploadinfo.addProperty("detailView",s);

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    sendreport.UploadDeal(Uploadinfo,new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            ldialog.dismiss();
                            AlertDialog dialog = new AlertDialog.Builder(MakeDealActivity.this).setMessage(result)
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
                            AlertDialog dialog = new AlertDialog.Builder(MakeDealActivity.this).setMessage("네트워크 문제로 등록에 실패하였습니다.\n잠시후 다시시도해주세요.")
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

        else if(requestCode == 2 && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath();

            Uri selectedImageUri1 = data.getData();
            imagepath1 = getPath(selectedImageUri1);

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;

            Bitmap bitmap1 = BitmapFactory.decodeFile(imagepath1,options);
            imageview1.setImageBitmap(bitmap1);

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
