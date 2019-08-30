package com.cxp.androidbase.broadcast_receiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;

/**
 * 文 件 名: BroadcastReceiverActivity
 * 创 建 人: CXP
 * 创建日期: 2019-08-26 15:55
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class BroadcastReceiverActivity extends BaseActivity {

    private MyReceiver myReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_broadcast_receiver);
    }

    public void clickLis(View view) {
        Intent intent = new Intent("com.cxp.androidbase.broadcast_receiver.MY_RECEIVER");
        //发送本地广播
        if (mLocalBroadcastManager!=null) {
            mLocalBroadcastManager.sendBroadcast(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.cxp.androidbase.broadcast_receiver.MY_RECEIVER");
        myReceiver=new MyReceiver();
        //本地广播
        mLocalBroadcastManager=LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(myReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (myReceiver!=null) {
            mLocalBroadcastManager.unregisterReceiver(myReceiver);
        }
    }
}
