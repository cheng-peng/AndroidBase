package com.cxp.androidbase;


import android.os.Handler;

/**
 * 文 件 名: SplashActivity
 * 创 建 人: CXP
 * 创建日期: 2019-09-03 17:14
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
            }
        },1000);
    }
}
