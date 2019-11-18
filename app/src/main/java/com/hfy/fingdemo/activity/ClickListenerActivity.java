package com.hfy.fingdemo.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dovar.dtoast.DToast;
import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import krt.wid.util.MToast;

public class ClickListenerActivity extends BaseActivity {
    @BindView(R.id.bt1)
    TextView bt1;
    @BindView(R.id.bt2)
    TextView bt2;
    @BindView(R.id.bt3)
    TextView bt3;
    @BindView(R.id.bt4)
    TextView bt4;

    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_click_listener;
    }

    @Override
    public void initView() {
        DToast.make(ClickListenerActivity.this)
                .setText(R.id.bt4,"和哈哈哈哈哈哈哈哈哈哈或或或")
                .setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 30)
                .show();
    }

    @Override
    public void loadData() {

    }


    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3,R.id.bt4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                Toast.makeText(ClickListenerActivity.this,"点击点击点击点击",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt2:
                DToast.make(ClickListenerActivity.this).show();
                break;
            case R.id.bt3:
                MToast.showToast(ClickListenerActivity.this,"8888888888888888");
                break;
            case R.id.bt4:
                DToast.make(ClickListenerActivity.this)
                        .setText(R.id.bt4,"和哈哈哈哈哈哈哈哈哈哈或或或")
                        .setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 30)
                        .show();
                break;
            default:
        }
    }
}
