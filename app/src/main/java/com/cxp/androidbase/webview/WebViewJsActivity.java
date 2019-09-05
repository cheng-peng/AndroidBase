package com.cxp.androidbase.webview;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;

/**
 * 文 件 名: WebViewJsActivity
 * 创 建 人: CXP
 * 创建日期: 2019-09-05 10:40
 * 描    述: WebView 与 JS交互
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class WebViewJsActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_webview_js);
        mWebView = findViewById(R.id.webview_js_webview);

        WebSettings mWebSettings = mWebView.getSettings();
        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(new JSBridge(), "android");

        mWebView.loadUrl("file:///android_asset/js.html");
        mWebView.setWebViewClient(mWebViewClient);
    }

    //设置不用系统浏览器打开,直接显示在当前Webview
    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    public void clickLis(View view) {

        // 因为evaluateJavascript方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript("javascript:sum(660,6)", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    Toast.makeText(getApplicationContext(), "Js返回值:" + value, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mWebView.loadUrl("javascript:sum(660,6)");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public class JSBridge {

        @JavascriptInterface
        public void toastMessage(String message) {
            Toast.makeText(getApplicationContext(), "Js调用Java:" + message, Toast.LENGTH_SHORT).show();
        }

    }
}

