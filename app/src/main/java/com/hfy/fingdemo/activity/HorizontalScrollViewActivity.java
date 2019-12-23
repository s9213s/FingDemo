package com.hfy.fingdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HorizontalScrollViewActivity extends Activity {
    private HorizontalScrollView ruler;
    private LinearLayout rulerlayout, all_layout;
    private TextView user_birth_value;
    private int beginYear;

    private String birthyear = "1970";
    private long time = 0;
    private int screenWidth;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scrollview);
        user_birth_value = (TextView) findViewById(R.id.user_birth_value);
        user_birth_value.setText("1970");
        ruler = (HorizontalScrollView) findViewById(R.id.birthruler);
        rulerlayout = (LinearLayout) findViewById(R.id.ruler_layout);
        ruler.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                user_birth_value.setText(String.valueOf(beginYear
                        + (int) Math.ceil((ruler.getScrollX()) / 20)));
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                user_birth_value.setText(String.valueOf(beginYear
                                        + (int) Math.ceil((ruler.getScrollX()) / 20)));
                                birthyear = String.valueOf((int) (beginYear + Math
                                        .ceil((ruler.getScrollX()) / 20)));
                                try {
                                    time = (new SimpleDateFormat("yyyy")
                                            .parse(String.valueOf(birthyear)))
                                            .getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 1000);
                        break;
                }
                return false;
            }

        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {
            screenWidth = ruler.getWidth();
            constructRuler();
            isFirst = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scroll();
            }
        }, 100);
    }

    private void scroll() {
        ruler.smoothScrollTo((1970 - beginYear) * 20, 0);
    }

    @SuppressWarnings("deprecation")
    private void constructRuler() {
        int year = new Date().getYear();
        if (year < 2015)
            year = 2010;
        beginYear = year / 10 * 10 - 150;
        View leftview = (View) LayoutInflater.from(this).inflate(
                R.layout.blankhrulerunit, null);
        leftview.setLayoutParams(new LayoutParams(screenWidth / 2,
                LayoutParams.MATCH_PARENT));
        rulerlayout.addView(leftview);
        for (int i = 0; i < 16; i++) {
            View view = (View) LayoutInflater.from(this).inflate(
                    R.layout.hrulerunit, null);
            view.setLayoutParams(new LayoutParams(200,
                    LayoutParams.MATCH_PARENT));
            TextView tv = (TextView) view.findViewById(R.id.hrulerunit);
            tv.setText(String.valueOf(beginYear + i * 10));
            rulerlayout.addView(view);
        }
        View rightview = (View) LayoutInflater.from(this).inflate(
                R.layout.blankhrulerunit, null);
        rightview.setLayoutParams(new LayoutParams(screenWidth / 2,
                LayoutParams.MATCH_PARENT));
        rulerlayout.addView(rightview);
    }

}