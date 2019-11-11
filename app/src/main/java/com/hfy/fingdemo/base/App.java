package com.hfy.fingdemo.base;

import android.util.Log;


import com.hfy.fingdemo.R;

import krt.wid.base.MApp;
import krt.wid.config.BaseModule;
import krt.wid.config.BaseModuleConfig;

public class App extends MApp {
    @Override
    protected void init() {

        BaseModule.initialize(BaseModuleConfig.newBuilder()
                .setLoadingViewColor(R.color.colorPrimary).build());
        //初始化X5内核
//        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
//            @Override
//            public void onCoreInitFinished() {
//                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
//                Log.e("App", "加载内核是否onCoreInitFinished成功:");
//            }
//
//            @Override
//            public void onViewInitFinished(boolean b) {
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.e("App", "加载内核是否成功:" + b);
//            }
//        });
    }
}
