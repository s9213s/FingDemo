package com.hfy.fingdemo.activity;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
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
    @BindView(R.id.add)
    ImageView add;

    private CustomPopWindow mCustomPopWindow;

    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_click_listener;
    }

    @Override
    public void initView() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopMenu();
            }
        });
    }

    private void showPopMenu() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.view_diagle, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.9f) // 控制亮度
                .create()
                .showAsDropDown(add, 0, 0);
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()) {
                    case R.id.text1:
                        showContent = "组群管理";
                        break;
                    case R.id.text2:
                        showContent = "创建群组";
                        break;
                    case R.id.text3:
                        showContent = "通讯录";
                        break;
                    default:
                }
                Toast.makeText(ClickListenerActivity.this, showContent, Toast.LENGTH_SHORT).show();
            }
        };
        contentView.findViewById(R.id.text1).setOnClickListener(listener);
        contentView.findViewById(R.id.text2).setOnClickListener(listener);
        contentView.findViewById(R.id.text3).setOnClickListener(listener);
    }

    @Override
    public void loadData() {

    }


    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                Toast.makeText(ClickListenerActivity.this, "点击了啊啊啊啊啊", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt2:
                SetToast("点击了啊啊啊啊啊");
                break;
            case R.id.bt3:
                MToast.showToast(ClickListenerActivity.this, "点击了啊啊啊啊啊");
                break;
            case R.id.bt4:
                if (!isFastClick()){
                    Log.w("hfydemo","*********************");
                }else {
                    Log.w("hfydemo","---------------------");
                }
                    break;
            default:
        }
    }

    //    防止多次点击造成的页面一直返回
    private static final int MIN_DELAY_TIME = 500;  // 两次点击间隔不能少于500ms 往大调也可以
    private static long lastClickTime;

    public static boolean isFastClick() { //这个方法可以放到公共类里
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
