package com.cxp.androidbase.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;

import java.io.InputStream;

/**
 * 文 件 名: DetailsActivity
 * 创 建 人: CXP
 * 创建日期: 2019-09-04 17:32
 * 描    述: WebView 详细使用
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class DetailsActivity extends BaseActivity {

    private final static String TAG = "CXP_LOG";

    private WebView mWebView;
    private ProgressBar mProgressBar;

    private String url = "https://www.baidu1.com";

    private Context mContext;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_basics);

        mContext = this;

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
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //方式一：加载一个网页
//        mWebView.loadUrl(url);

        //方式二：加载应用资源文件内的网页
//        mWebView.loadUrl("file:///android_asset/chrome.html");

        //方式三：加载一段代码（loadData和loadDataWithBaseURL类似，后者兼容性更好）
        //内容里不能出现 ’#’, ‘%’, ‘\’ , ‘?’ 这四个字符，若出现了需用 %23, %25, %27, %3f 对应来替代，否则会出现异常
        String baseURL = "http://img4.imgtn.bdimg.com";
        String data = "漂亮MM <img src='/it/u=3136075639,3338708347&fm=26&gp=0.jpg'>";
        mWebView.loadDataWithBaseURL(baseURL, data, "text/html", "utf-8", null);

        //处理各种通知 & 请求事件
        mWebView.setWebViewClient(mWebViewClient);
        //辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
        mWebView.setWebChromeClient(mWebChromeClient);

        mWebView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView)v).getHitTestResult();
                if (null == result)
                    return false;
                int type = result.getType();
                //未知类型
                if (type == WebView.HitTestResult.UNKNOWN_TYPE)
                    return false;

                // 这里可以拦截很多类型，我们只处理图片类型就可以了
                switch (type) {
                    case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // 地图类型
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE://带有链接的图片类型
                        break;
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                        // 获取图片的路径
                        String saveImgUrl = result.getExtra();
                        Log.e(TAG,"image："+saveImgUrl);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    //设置不用系统浏览器打开,直接显示在当前Webview
    private WebViewClient mWebViewClient = new WebViewClient() {

        //拦截网络请求  兼容5.0以下
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //把含有blog.csdn.net的网址改成百度
            if (url.contains("blog.csdn.net")) {
                view.loadUrl("http://www.baidu.com");
            }
            view.loadUrl(url);

            //返回true webview 就不会触发系统默认的连接，而是我们提供的连接，false 继续加载
            return true;
        }

        //兼容5.0以上
        @SuppressLint("NewApi")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().contains("blog.csdn.net")) {
                view.loadUrl("http://www.baidu.com");
            }
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        //加载SSL出错的时候调用，出错会出现白页
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //必须注释，因为里面有handler.cancel();
//            super.onReceivedSslError(view, handler, error);

            //忽略错误，继续访问
            handler.proceed();
        }

        //加载出错时候调用
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            mWebView.loadUrl("file:///android_asset/error.html");
        }

        //每一次请求资源都会调用   （在非UI线程里使用的，所以不能直接操作UI，需要handler）
        @SuppressLint("NewApi")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            //替换本地图片
            try {
                if (request.getUrl().toString().equals("http://img4.imgtn.bdimg.com/it/u=3136075639,3338708347&fm=26&gp=0.jpg")) {
                    AssetFileDescriptor fileDescriptor = getAssets().openFd("cxp.png");
                    InputStream stream = fileDescriptor.createInputStream();
                    WebResourceResponse response = new WebResourceResponse("image/png", "UTF-8", stream);
                    return response;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.shouldInterceptRequest(view, request);
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


    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        //获得网页的加载进度并显示
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress < 100) {
                String progress = newProgress + "%";
                Log.e(TAG, progress);
            }
        }

        //获取Web页中的标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.e(TAG, title);
        }

        //支持javascript的警告框（JS中的alert窗口）
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(mContext)
                    .setTitle("JsAlert")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setCancelable(false)
                    .show();

            //部分机型只会弹出一次提示框，调用此方法即可解决此问题。
//            result.cancel();

            //返回true表示不弹出系统的提示框，返回false表示弹出
            return true;
        }

        //支持javascript的确认框
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(mContext)
                    .setTitle("JsConfirm")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    })
                    .setCancelable(false)
                    .show();
            //返回true表示不弹出系统的提示框，返回false表示弹出
            return true;
        }

        //支持javascript输入框
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
            final EditText et = new EditText(mContext);
            et.setText(defaultValue);
            new AlertDialog.Builder(mContext)
                    .setTitle(message)
                    .setView(et)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm(et.getText().toString());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    })
                    .setCancelable(false)
                    .show();
            return true;
        }

        //拦截Console,弹出消息
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Toast.makeText(mContext,consoleMessage.message(),Toast.LENGTH_SHORT).show();
            return  true;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        //小心这个！！！暂停整个应用的 WebView 所有布局、解析、JS。
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
