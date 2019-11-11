package com.hfy.fingdemo.web;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;


import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseFragment;

import butterknife.BindView;

/**
 * @author xzy
 * @package krt.com.zhdn.fragment
 * @description
 * @time 2018/10/19
 */
public class WebFragment extends BaseFragment {
    @BindView(R.id.web)
    MyWebView mWebView;
    @BindView(R.id.invalidLayout)
    LinearLayout invalidLayout;

    private String url;

    private boolean isLoader = false;

    private String js = "javascript:(function(){" +
            "var objs = document.getElementsByTagName('img'); " +
            "for(var i=0;i<objs.length;i++)  " +
            "{"
            + "var img = objs[i];   " +
            "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
            "}" +
            "})()";

    public WebFragment setUrl(String url) {
        this.url = url;
        return this;
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_web;
    }

    @Override
    public void initView(View view) {
        initWebSetting();
    }

    private void initWebSetting() {
        if (url == null || url.equals("")){ return;}
        if (url.startsWith("http://") || url.startsWith("https://")) {
            mWebView.setVisibility(View.VISIBLE);
            invalidLayout.setVisibility(View.GONE);
            mWebView.setClickable(true);
            mWebView.setDisplayZoomControls(false);
            mWebView.setMixedContentMode();
            mWebView.setBuiltInZoomControls(false);
            mWebView.setJavaScriptEnabled(true);
            mWebView.setDomStorageEnabled(true);
            mWebView.setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.setCacheMode(WebSettings.LOAD_DEFAULT);
            mWebView.setLoadWithOverviewMode(true);
            mWebView.setUseWideViewPort(true);
            mWebView.setLayoutAlgorithm();
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

                @Override
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

    @Override
    public void loadData() {
        if (!isLoader) {
            mWebView.loadUrl(url);
            isLoader = true;
        }
    }


    @Override
    public void onDestroyView() {
        try {
            mWebView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }

}
