package com.chocoroll.buyto.Model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by HyeJi on 2015. 5. 28..
 */
public class MyTextView extends TextView {

    public MyTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/10X10.ttf"));
    }
}
