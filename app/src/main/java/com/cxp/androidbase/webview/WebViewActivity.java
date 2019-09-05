package com.cxp.androidbase.webview;

import android.view.View;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;

/**
 * 文 件 名: WebViewActivity
 * 创 建 人: CXP
 * 创建日期: 2019-08-30 10:49
 * 描    述: WebView 使用
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class WebViewActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.activity_webview);
    }

    public void clickLis(View view){
        switch (view.getId()) {
            case R.id.webview_bt1:
                //WebView 基础使用
                startActivity(BasicsActivity.class);
                break;
            case R.id.webview_bt2:
                //WebView 详细使用
                startActivity(DetailsActivity.class);
                break;
            case R.id.webview_bt3:
                //WebView Java与Js交互
                startActivity(WebViewJsActivity.class);
                break;
        }
    }
}
