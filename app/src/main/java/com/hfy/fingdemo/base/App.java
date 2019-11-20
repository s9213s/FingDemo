package com.hfy.fingdemo.base;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;


import com.hfy.fingdemo.R;
import com.hfy.fingdemo.demo.util.CrashUtils;
import com.hfy.fingdemo.demo.util.LogUtil;
import com.hfy.fingdemo.test.database.greenDao.db.DaoMaster;
import com.hfy.fingdemo.test.database.greenDao.db.DaoSession;

import krt.wid.base.MApp;
import krt.wid.config.BaseModule;
import krt.wid.config.BaseModuleConfig;

public class App extends MApp {
    public static App sInstance;

    public Handler mHandler;

    public static App getInstance() {
        return sInstance;
    }
    @Override
    protected void init() {
        try {
            LogUtil.i("onCreate...");

            mHandler = new Handler();
            sInstance = this;

            CrashUtils.init(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "aserbao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;
    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.i("onTerminate...");

        mHandler = null;
        sInstance = null;
    }
}
