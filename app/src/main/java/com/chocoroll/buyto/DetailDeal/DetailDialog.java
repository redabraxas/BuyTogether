package com.chocoroll.buyto.DetailDeal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chocoroll.buyto.Extra.DownloadImageTask;
import com.chocoroll.buyto.R;

/**
 * Created by HyeJi on 2015. 6. 4..
 */
public class DetailDialog extends Dialog{

    String url;

    public DetailDialog(Context context, String url) {
        super(context);
        this.url =url;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail_deal);


        new DownloadImageTask((ImageView) findViewById(R.id.detailDealImage))
                .execute(url);

        Button btn = (Button)findViewById(R.id.btnFinish);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
