package com.cxp.androidbase;

import android.view.View;

import com.cxp.androidbase.broadcast_receiver.BroadcastReceiverActivity;
import com.cxp.androidbase.content_provider.ContentProviderActivity;
import com.cxp.androidbase.sqlite.SQLiteActivity;
import com.cxp.androidbase.webview.WebViewActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
    }

    public void clickLis(View view) {
        switch (view.getId()) {
            case R.id.main_bt1:
                //BroadcastReceiver 广播
                startActivity(BroadcastReceiverActivity.class);
                break;
            case R.id.main_bt2:
                //SQLite 数据库
                startActivity(SQLiteActivity.class);
                break;
            case R.id.main_bt3:
                //ContentProvider 存储和数据共享
                startActivity(ContentProviderActivity.class);
                break;
            case R.id.main_bt4:
                //WebView 网页
                startActivity(WebViewActivity.class);
                break;
        }
    }
}
