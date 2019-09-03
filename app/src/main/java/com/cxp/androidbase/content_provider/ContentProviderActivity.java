package com.cxp.androidbase.content_provider;

import android.view.View;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;
import com.cxp.androidbase.content_provider.custom.CustomUriActivity;
import com.cxp.androidbase.content_provider.system.SystemUriActivity;

/**
 * 文 件 名: ContentProviderActivity
 * 创 建 人: CXP
 * 创建日期: 2019-08-30 14:04
 * 描    述: 存储和数据共享
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class ContentProviderActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.activity_content_provider);
    }

    public void clickLis(View view) {
        switch (view.getId()) {
            case R.id.cp_bt1:
                //常用 Uri
                startActivity(SystemUriActivity.class);
                break;
            case R.id.cp_bt2:
                //自定义Uri
                startActivity(CustomUriActivity.class);
                break;
        }
    }
}
