package com.hfy.fingdemo.base;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.hfy.fingdemo.R;
import com.hfy.fingdemo.web.X5WebFragment;

import butterknife.BindView;
import krt.wid.util.MTitle;

public class X5WebActivity extends BaseActivity {
    @BindView(R.id.title)
    MTitle mTitle;

    private String url;

    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        String title = getIntent().getStringExtra("title");
        if (title == null) {
            mTitle.setVisibility(View.GONE);
        }
        url = getIntent().getStringExtra("url");
        mTitle.setCenterText(getIntent().getStringExtra("title"),18, ContextCompat.getColor(this, R.color.color_FFFFFE));
        getSupportFragmentManager().beginTransaction().add(R.id.content,  new X5WebFragment().setUrl(url)).commit();
    }

    @Override
    public void loadData() {

    }
    //s 42 106 58 57  m 45 110 60 59
    //s 42 106 59 58  m 45 110 60 59
}
