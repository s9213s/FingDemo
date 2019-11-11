
package com.hfy.fingdemo.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import krt.wid.http.LoadingDialog;

/**
 * 备注: 此LJWebView继承自Relativielayout,所以会导致丢失一个WebView的属性，如果大家
 * 在项目中需要用到，可是此类中加入，然后调用即可，可参考 public void setClickable(boolean value){
 * mWebView.setClickable(value); } 这个方法的定义和调用
 *
 * @author Administrator
 */
@SuppressLint({"SetJavaScriptEnabled", "InflateParams"})
public class MyWebView extends RelativeLayout {

    private Context context;

    private Activity activity;

    private WebView mWebView = null; //

    private LoadingDialog mDialog;


    public MyWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        mWebView = new WebView(context);
        this.addView(mWebView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        mDialog = new LoadingDialog(context, "LineScaleIndicator");
        // 点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        mWebView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                            && mWebView.canGoBack()) { // 表示按返回键时的操作
                        mWebView.goBack(); // 后退
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            }
        });

    }

    @Override
    public void setClickable(boolean value) {
        mWebView.setClickable(value);
    }

    /**
     * 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
     */
    public void setJavaScriptEnabled(boolean value) {
        mWebView.getSettings().setJavaScriptEnabled(value);
    }

    /**
     * 设置自适应屏幕，两者合用
     * 将图片调整到适合webview的大小
     *
     * @param value
     */
    public void setUseWideViewPort(boolean value) {
        mWebView.getSettings().setUseWideViewPort(value);
    }

    /**
     * 设置自适应屏幕，两者合用
     * 将图片调整到适合webview的大小
     *
     * @param value
     */
    public void setLoadWithOverviewMode(boolean value) {
        mWebView.getSettings().setLoadWithOverviewMode(value);
    }

    /**
     * 支持缩放
     *
     * @param value
     */
    public void setSupportZoom(boolean value) {
        mWebView.getSettings().setSupportZoom(value);
    }

    /**
     * 设置内置的缩放控件。若为false，则该WebView不可缩放
     *
     * @param value
     */
    public void setBuiltInZoomControls(boolean value) {
        mWebView.getSettings().setBuiltInZoomControls(value);
    }

    /**
     * 是否隐藏隐藏原生的缩放控件
     *
     * @param value
     */
    public void setDisplayZoomControls(boolean value) {
        mWebView.getSettings().setDisplayZoomControls(value);
    }

    /**
     * 混合加载  Http和Https混合加载 5.0以后解决方案~
     * 1.MIXED_CONTENT_ALWAYS_ALLOW 允许从任何来源加载内容，即使起源是不安全的；
     * 2.MIXED_CONTENT_NEVER_ALLOW 不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
     * 3.MIXED_CONTENT_COMPLTIBILITY_MODE 当涉及到混合式内容时，WebView会尝试去兼容最新Web浏览器的
     * 风格；
     */
    public void setMixedContentMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 是否阻止加载网络图片
     *
     * @param value
     */
    public void setBlockNetworkImage(boolean value) {
        mWebView.getSettings().setBlockNetworkImage(true);
    }


    public void setJavaScriptCanOpenWindowsAutomatically(boolean value) {
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(value);
    }

    public void setCacheMode(int value) {
        mWebView.getSettings().setCacheMode(value);
    }

    public void setWebViewClient(WebViewClient value) {
        mWebView.setWebViewClient(value);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public void reload() {
        mWebView.reload();
    }

    public void postUrl(String url, byte[] post) {
        mWebView.postUrl(url, post);
    }

    public void addJavascriptInterface(Object object, String name) {
        mWebView.addJavascriptInterface(object, name);
    }

    public void setAppCacheEnabled(boolean flag) {
        mWebView.getSettings().setAppCacheEnabled(flag);

    }

    public void setDatabaseEnabled(boolean flag) {
        mWebView.getSettings().setDatabaseEnabled(flag);
    }

    public void setDomStorageEnabled(boolean flag) {
        mWebView.getSettings().setDomStorageEnabled(flag);
    }

    public void setAppCachePath(String appCachePath) {
        mWebView.getSettings().setAppCachePath(appCachePath);
    }

    @SuppressWarnings("deprecation")
    public void setDatabasePath(String databasePath) {
        mWebView.getSettings().setDatabasePath(databasePath);
    }

    public void setAllowFileAccess(boolean flag) {
        mWebView.getSettings().setAllowFileAccess(flag);
    }

    @SuppressWarnings("deprecation")
    public void setAppCacheMaxSize(long appCacheMaxSize) {
        mWebView.getSettings().setAppCacheMaxSize(appCacheMaxSize);
    }

    public void setLayoutAlgorithm() {
        mWebView.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }


    @SuppressWarnings("deprecation")
    public void setSavePassword(boolean value) {
        mWebView.getSettings().setSavePassword(value);
    }

    public void setSaveFormData(boolean value) {
        mWebView.getSettings().setSaveFormData(value);
    }

    // enable navigator.geolocation
    public void setGeolocationEnabled(boolean value) {
        mWebView.getSettings().setGeolocationEnabled(value);
    }

    public void setGeolocationDatabasePath(String path) {
        mWebView.getSettings().setGeolocationDatabasePath(path);
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        mWebView.setDownloadListener(downloadListener);
    }

    public String getUrl() {
        return mWebView.getUrl();
    }

    public void onPause() {
        mWebView.onPause();
    }

    public void destroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
    }

    /**
     * 使用GoBack方法
     */
    public void GoBack() {
        mWebView.goBack();
    }
}