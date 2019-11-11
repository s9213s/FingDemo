package com.hfy.fingdemo.web;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.hfy.fingdemo.BuildConfig;
import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseFragment;

import butterknife.BindView;
import krt.wid.util.MPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * @author Marcus
 * @package com.krt.zhdn.fragment
 * @description
 * @time 2018/11/3
 */
public class X5WebFragment extends BaseFragment {
    @BindView(R.id.web)
    X5WebView mWebView;
    @BindView(R.id.invalidLayout)
    LinearLayout invalidLayout;
    private String url;

    private boolean isLoader = false;

    private String jsName = "";

    private String jsVideoName = "";

    private String jsRecordName = "";

    private static final int GET_SCAN = 2;

    /**
     * js定位名称
     */
    private String localName = "";

    public X5WebFragment setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_web_x5;
    }

    @Override
    public void initView(View view) {
        initWebSetting();
    }

    private void initWebSetting() {
        if (url == null || url.equals("")) {
            return;
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            mWebView.setVisibility(View.VISIBLE);
            invalidLayout.setVisibility(View.GONE);
            mWebView.setClickable(true);
            mWebView.setDisplayZoomControls(false);
            mWebView.setBuiltInZoomControls(false);
            mWebView.setJavaScriptEnabled(true);
            mWebView.setDomStorageEnabled(true);
            mWebView.setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.setCacheMode(BuildConfig.DEBUG ? WebSettings.LOAD_NO_CACHE : WebSettings.LOAD_DEFAULT);
            mWebView.setLoadWithOverviewMode(true);
            mWebView.setUseWideViewPort(true);
            mWebView.setAllowFileAccess(true);
            mWebView.addJavascriptInterface(this, "MyJS");
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                    } else {
                        try {
                            Uri uri = Uri.parse(url);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }

                public void onReceivedSslError(WebView view,
                                               SslErrorHandler handler, SslError error) {


                    handler.proceed();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
        } else {
            mWebView.setVisibility(View.GONE);
            invalidLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setNewUrl(String newUrl) {
        if (mWebView != null) {
            mWebView.loadUrl(newUrl);
        }
    }

    @Override
    public void loadData() {
        if (mWebView != null && !isLoader) {
            mWebView.loadUrl(url);
            isLoader = true;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoader = false;
    }

    @Override
    public void onDestroy() {
        try {
            mWebView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    /**
     * 跳转web
     *
     * @param WebUrl
     */
    @JavascriptInterface
    public void goToWebUrl(String WebUrl) {
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra("url", WebUrl);
        intent.putExtra("isX5Web", true);
        startActivity(intent);
    }

    @JavascriptInterface
    public void goToNativeUrl(String nativeUrl) {

    }

    /**
     * 跳转web页，并显示标题栏
     *
     * @param webUrl
     * @param showTitle
     */
    @JavascriptInterface
    public void gotoWebActivity(String webUrl, boolean showTitle) {
        if (showTitle) {
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra("title", "");
            intent.putExtra("url", webUrl);
            intent.putExtra("isX5Web", true);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra("url", webUrl);
            intent.putExtra("isX5Web", true);
            startActivity(intent);
        }

    }
}
