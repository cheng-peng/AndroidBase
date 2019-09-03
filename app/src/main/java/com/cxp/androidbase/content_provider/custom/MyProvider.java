package com.cxp.androidbase.content_provider.custom;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 文 件 名: MyProvider
 * 创 建 人: CXP
 * 创建日期: 2019-09-02 14:19
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class MyProvider extends ContentProvider {

    private Context mContext;
    private DBHelper mDbHelper = null;
    private SQLiteDatabase db = null;
    private static final UriMatcher mMatcher;

    public static final int USER_CODE_ITEM = 1;
    public static final int USER_CODE_DIR = 2;
    public static final int JOB_CODE_ITEM = 3;
    public static final int JOB_CODE_DIR = 4;

    public static final String AUTOHORITY = "com.cxp.androidbase.provider";

    static {
        //常量UriMatcher.NO_MATCH  = 不匹配任何路径的返回码
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTOHORITY, "user/#", USER_CODE_ITEM);
        mMatcher.addURI(AUTOHORITY, "user", USER_CODE_DIR);
        mMatcher.addURI(AUTOHORITY, "job/#", JOB_CODE_ITEM);
        mMatcher.addURI(AUTOHORITY, "job", JOB_CODE_DIR);
    }

    //返回true表示初始化成功
    @Override
    public boolean onCreate() {

        mContext=getContext();

        // 在ContentProvider创建时对数据库进行初始化
        // 运行在主线程，故不能做耗时操作,此处仅作展示
        mDbHelper = new DBHelper(getContext());
        db = mDbHelper.getWritableDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        String table = "";
        switch (mMatcher.match(uri)) {
            case USER_CODE_DIR:
                table = DBHelper.USER_TABLE_NAME;
                break;
            case JOB_CODE_DIR:
                table = DBHelper.JOB_TABLE_NAME;
                break;
        }
        return db.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long row;
        String table = "";
        switch (mMatcher.match(uri)) {
            case USER_CODE_ITEM:
                table = DBHelper.USER_TABLE_NAME;
                long personid = ContentUris.parseId(uri);
                values.put("_id", personid);
                break;
            case USER_CODE_DIR:
                table = DBHelper.USER_TABLE_NAME;
                break;
            case JOB_CODE_ITEM:
                table = DBHelper.JOB_TABLE_NAME;
                values.put("_id", ContentUris.parseId(uri));
                break;
            case JOB_CODE_DIR:
                table = DBHelper.JOB_TABLE_NAME;
                break;
        }
        row= db.insert(table, null, values);
        if (row>0) {
            //通知访问者ContentObserver，会执行onChange回调
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int row;
        String table = "";
        switch (mMatcher.match(uri)) {
            case USER_CODE_DIR:
                table = DBHelper.USER_TABLE_NAME;
                break;
            case JOB_CODE_DIR:
                table = DBHelper.JOB_TABLE_NAME;
                break;
        }
        row=db.delete(table, selection, selectionArgs);
        if (row>0) {
            //通知访问者ContentObserver，会执行onChange回调
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return row;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int row;
        String table = "";
        switch (mMatcher.match(uri)) {
            case USER_CODE_DIR:
                table = DBHelper.USER_TABLE_NAME;
                break;
            case JOB_CODE_DIR:
                table = DBHelper.JOB_TABLE_NAME;
                break;
        }
        row= db.update(table, values, selection, selectionArgs);
        if (row>0) {
            //通知访问者ContentObserver，会执行onChange回调
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return row;
    }

}
