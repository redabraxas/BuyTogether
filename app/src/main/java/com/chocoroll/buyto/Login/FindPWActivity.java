package com.chocoroll.buyto.Login;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;

import com.chocoroll.buyto.R;

public class FindPWActivity extends FragmentActivity {

    Button send;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);
        // 아이디 입력 후 임시 비밀번호 발송
        user_email = ((EditText)findViewById(R.id.user_email)).getText().toString();

        send = (Button)findViewById(R.id.send);



    }

}
