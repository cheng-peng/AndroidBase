package com.cxp.androidbase.content_provider.custom;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;

/**
 * 文 件 名: CustomUriActivity
 * 创 建 人: CXP
 * 创建日期: 2019-09-02 14:48
 * 描    述: 自定义URI
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class CustomUriActivity extends BaseActivity {

    private ContentObserver mContentObserver;
    private Handler mHandler;
    private Context mContext;


    @SuppressLint("HandlerLeak")
    private class ObserverHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_custom_uri);

        mContext = this;
        mHandler = new ObserverHandler();

        mContentObserver = new ContentObserver(mHandler) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                //数据库执行完回调
                Message message = new Message();
                message.obj = "完成";
                mHandler.sendMessage(message);
            }
        };

        //注册观察者ContentObserver
        mContext.getContentResolver().registerContentObserver(Uri.parse("content://com.cxp.androidbase.provider/user"), true, mContentObserver);
    }

    public void clickLis(View view) {
        switch (view.getId()) {
            case R.id.curi_bt1:
                //插入数据
                Uri uri_user = Uri.parse("content://com.cxp.androidbase.provider/user");
                // 插入表中数据
                ContentValues values_user = new ContentValues();
                values_user.put("_id", "1");
                values_user.put("name", "CXP");
                // 获取ContentResolver
                ContentResolver resolver_user = getContentResolver();
                // 通过ContentResolver 根据URI 向ContentProvider中插入数据
                resolver_user.insert(uri_user, values_user);

                Uri uri_job = Uri.parse("content://com.cxp.androidbase.provider/job");
                // 插入表中数据
                ContentValues values_job = new ContentValues();
                values_job.put("_id", "1");
                values_job.put("job", "Android");
                // 获取ContentResolver
                ContentResolver resolver = getContentResolver();
                // 通过ContentResolver 根据URI 向ContentProvider中插入数据
                resolver.insert(uri_job, values_job);
                break;
            case R.id.curi_bt2:
                //修改数据
                Uri uri_update = Uri.parse("content://com.cxp.androidbase.provider/user");
                // 插入表中数据
                ContentValues values_update = new ContentValues();
                values_update.put("_id", "2");
                values_update.put("name", "程小鹏");
                // 获取ContentResolver
                ContentResolver resolver_update = getContentResolver();
                // 通过ContentResolver 根据URI 向ContentProvider中插入数据
                resolver_update.update(uri_update, values_update, "_id=?", new String[]{"1"});
                break;
            case R.id.curi_bt3:
                //查询数据
                Uri uri_query = Uri.parse("content://com.cxp.androidbase.provider/user");
                // 插入表中数据
                // 获取ContentResolver
                ContentResolver resolver_query = getContentResolver();
                // 通过ContentResolver 根据URI 向ContentProvider中插入数据
                Cursor cursor = resolver_query.query(uri_query, new String[]{"_id", "name"}, null, null);
                while (cursor.moveToNext()) {
                    System.out.println("query user:" + cursor.getInt(0) + " " + cursor.getString(1));
                }
                cursor.close();
                break;
            case R.id.curi_bt4:
                //删除数据
                Uri uri_delete = Uri.parse("content://com.cxp.androidbase.provider/user");
                // 获取ContentResolver
                ContentResolver resolver_delete = getContentResolver();
                // 通过ContentResolver 根据URI 向ContentProvider中插入数据
                resolver_delete.delete(uri_delete, "_id=?", new String[]{"2"});
                break;
            case R.id.curi_bt5:
                //插入数据_id为111
                Uri uri1 = Uri.parse("content://com.cxp.androidbase.provider/user");
                uri1 = ContentUris.withAppendedId(uri1, 111);
                // 插入表中数据
                ContentValues values1 = new ContentValues();
                values1.put("name", "CXP");
                // 获取ContentResolver
                ContentResolver resolver1 = getContentResolver();
                // 通过ContentResolver 根据URI 向ContentProvider中插入数据
                resolver1.insert(uri1, values1);

                Uri uri2 = Uri.parse("content://com.cxp.androidbase.provider/job/111");
                // 插入表中数据
                ContentValues values2 = new ContentValues();
                values2.put("job", "Android");
                // 获取ContentResolver
                ContentResolver resolver2 = getContentResolver();
                // 通过ContentResolver 根据URI 向ContentProvider中插入数据
                resolver2.insert(uri2, values2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册观察者ContentObserver
        if (mContentObserver != null) {
            mContext.getContentResolver().unregisterContentObserver(mContentObserver);
        }
    }
}
