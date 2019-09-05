package com.cxp.androidbase.scheme;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.MainActivity;

import java.util.Set;

/**
 * 文 件 名: IntentParseActivity
 * 创 建 人: CXP
 * 创建日期: 2019-09-03 16:23
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class IntentParseActivity extends BaseActivity {

    private final static String TAG = "CXP_LOG";

    private Context mContext;

    @Override
    protected void initView() {

        mContext = this;

        if (getIntent() != null) {
            Uri data = getIntent().getData();
            String action = getIntent().getAction();
            Set<String> categories = getIntent().getCategories();
            String scheme = getIntent().getScheme();
            String host = data.getHost();
            String path = data.getPath();
            int port = data.getPort();
            Set<String> paramNames = data.getQueryParameterNames();
            String name = data.getQueryParameter("name");
            String id = data.getQueryParameter("id");

            Log.d(TAG, "============>action：" + action + "<============");
            Log.d(TAG, "============>categories：" + categories + "<============");
            Log.d(TAG, "============>scheme：" + scheme + "<============");
            Log.d(TAG, "============>host：" + host + "<============");
            Log.d(TAG, "============>path：" + path + "<============");
            Log.d(TAG, "============>port：" + port + "<============");
            Log.d(TAG, "============>paramNames：" + paramNames + "<============");
            Log.d(TAG, "============>name：" + name + "<============");
            Log.d(TAG, "============>id：" + id + "<============");

            Intent intent1 = new Intent(mContext, MainActivity.class);
            Intent intent2 = new Intent(mContext, SchemeActivity.class);
            intent2.putExtra("name", name);
            TaskStackBuilder.create(mContext)
                    .addNextIntent(intent1)
                    .addNextIntent(intent2)
                    .startActivities();

            finish();
        }
    }
}
