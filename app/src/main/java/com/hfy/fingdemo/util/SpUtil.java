package com.hfy.fingdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import krt.wid.util.ParseJsonUtil;

public class SpUtil {
    private static final String USER = "user";
    private Context mContext;
    private SharedPreferences sp;


    public SpUtil(Context context) {
        this.mContext = context;
        sp = context.getSharedPreferences(USER, 0);
    }

    /**
     * 获取token
     */
    //用户token
    public void setToken(String token) {
        sp.edit().putString("token", token).commit();
    }

    public String getAccessToken() {
        return sp.getString("token", "");
    }

    /**
     * 设置/获取是否已登录
     */
    public boolean isLogin() {
        return !TextUtils.isEmpty(getAccessToken());
    }

    public void setIsLogin(boolean auto) {
        sp.edit().putBoolean("islogin", auto).commit();
    }

    public boolean getIsLogin() {
        return sp.getBoolean("islogin", false);
    }

//    public void setUserInfo(String info) {
//        SharedPreferences.Editor edit = mContext.getSharedPreferences(USER, Context.MODE_PRIVATE).edit();
//        edit.putString("user", info).apply();
//    }
//
//    public UserInfoBean getUserInfo() {
//        SharedPreferences edit = mContext.getSharedPreferences(USER, Context.MODE_PRIVATE);
//        return ParseJsonUtil.getBean(edit.getString("user", ""), UserInfoBean.class);
//    }
}
