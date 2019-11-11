package com.hfy.fingdemo.web;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseActivity;

import butterknife.BindView;
import krt.wid.util.MTitle;

/**
 * @author xzy
 * @package krt.com.zhdn.activity
 * @description
 * @time 2018/10/31
 */
public class WebActivity extends BaseActivity {
    @BindView(R.id.title)
    MTitle mTitle;

    private String url;

    private WebFragment mWebFragment;

    private boolean localweb; //是否使用原生的web进行加载

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
        localweb = getIntent().getBooleanExtra("isX5Web", false);
        url = getIntent().getStringExtra("url");
        mTitle.setCenterText(getIntent().getStringExtra("title"),18, ContextCompat.getColor(this, R.color.color_FFFFFE));
        getSupportFragmentManager().beginTransaction().add(R.id.content,  new X5WebFragment().setUrl(url)).commit();
    }

    @Override
    public void loadData() {

    }


}
