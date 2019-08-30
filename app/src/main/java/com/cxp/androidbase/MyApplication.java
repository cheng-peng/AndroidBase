package com.cxp.androidbase;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * 文 件 名: MyApplication
 * 创 建 人: CXP
 * 创建日期: 2019-08-30 9:40
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //调试工具
        Stetho.initializeWithDefaults(this);
    }
}
