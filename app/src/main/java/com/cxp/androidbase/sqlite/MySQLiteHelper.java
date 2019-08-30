package com.cxp.androidbase.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 文 件 名: MySQLiteHelper
 * 创 建 人: CXP
 * 创建日期: 2019-08-30 9:21
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // 数据库版本号
    private static Integer mVersion = 1;

    /**
     * 构造函数
     * 在SQLiteOpenHelper的子类中，必须有该构造函数
     * context：上下文对象
     * name：数据库名称
     * param：一个可选的游标工厂（通常是 Null）
     * version：当前数据库的版本，值必须是整数并且是递增的状态
     */
    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        // 必须通过super调用父类的构造函数
        super(context, name, factory, version);
    }

    public MySQLiteHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }


    public MySQLiteHelper(Context context, String name) {
        //数据库版本号1
        this(context, name, mVersion);
    }

    /**
     * 复写onCreate（）
     * 调用时刻：当数据库第1次创建时调用
     * 作用：创建数据库 表 & 初始化数据
     * SQLite数据库创建支持的数据类型： 整型数据、字符串类型、日期类型、二进制
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("=============>创建数据库和表<=============");
        // 创建数据库1张表
        // 通过execSQL（）执行SQL语句（此处创建了1个名为user的表）
        // SQLite数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
        // 注：数据库实际上是没被创建 / 打开的（因该方法还没调用）
        // 直到getWritableDatabase() / getReadableDatabase() 第一次被调用时才会进行创建 / 打开
        String sql = "create table user(id int primary key,name varchar(200))";
        db.execSQL(sql);
    }

    /**
     * 数据库升级时调用
     * 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade（）方法
     * 作用：更新数据库表结构
     * 注：创建SQLiteOpenHelper子类对象时,必须传入一个version参数，该参数 = 当前数据库版本, 若该版本高于之前版本, 就调用onUpgrade()
     * db ： 数据库
     * oldVersion ： 旧版本数据库
     * newVersion ： 新版本数据库
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("=============>更新数据库版本为:" + newVersion + "<=============");
    }
}
