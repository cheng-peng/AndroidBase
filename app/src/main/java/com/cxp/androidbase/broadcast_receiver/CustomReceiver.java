package com.cxp.androidbase.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cxp.androidbase.utils.AppUtls;

/**
 * 文 件 名: MyReceiver
 * 创 建 人: CXP
 * 创建日期: 2019-08-26 16:03
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class CustomReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(AppUtls.TAG,"===============>CustomReceiver<===============");
    }
}
