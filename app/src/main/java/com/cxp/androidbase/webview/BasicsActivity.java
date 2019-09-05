package com.cxp.androidbase.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;

/**
 * 文 件 名: BasicsActivity
 * 创 建 人: CXP
 * 创建日期: 2019-09-04 16:42
 * 描    述: 基础使用
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class BasicsActivity extends BaseActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    private String url = "https://www.baidu.com";

    @Override
    protected void initView() {
        setContentView(R.layout.activity_basics);
        mProgressBar = findViewById(R.id.webview_pb);
        mWebView = findViewById(R.id.webview_webview);

        WebSettings mWebSettings = mWebView.getSettings();
        //setUseWideViewPort和setLoadWithOverviewMode合用
        //将图片调整到适合webview的大小
        mWebSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        mWebSettings.setLoadWithOverviewMode(true);
        //支持自动加载图片
        mWebSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);
        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启   (请求https连接时候)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**
             * MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
             * MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
             * MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
             *
             * 在5.0以下 Android 默认是 全允许，
             * 但是到了5.0以上，就是不允许，实际情况下很我们很难确定所有的网页都是https的，所以就需要这一步的操作。
             */
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.loadUrl(url);
        mWebView.setWebViewClient(mWebViewClient);
    }

    //设置不用系统浏览器打开,直接显示在当前Webview
    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        //加载SSL出错的时候调用，出错会出现白页
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //忽略错误，继续访问
            handler.proceed();
        }


        //开始加载网页时调用
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //禁止用户与界面继续交互的效果
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        //加载结束时调用
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //恢复用户与界面继续交互的效果
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mProgressBar.setVisibility(View.GONE);
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        //小心这个！！！暂停整个 WebView 所有布局、解析、JS。
        mWebView.pauseTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }


    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressBar != null) {
            mProgressBar = null;
        }
        if (mWebView != null) {
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
    }
}
