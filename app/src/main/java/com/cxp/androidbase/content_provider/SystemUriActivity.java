package com.cxp.androidbase.content_provider;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;

/**
 * 文 件 名: SystemUriActivity
 * 创 建 人: CXP
 * 创建日期: 2019-08-30 16:31
 * 描    述:
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class SystemUriActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.activity_system_uri);
    }

    public void clickLis(View view) {
        switch (view.getId()) {
            case R.id.su_bt1:
                //获取通讯录
                    checkPermission(new CheckPermListener() {
                        @Override
                        public void superPermission() {
                            startActivity(ReadContactsActivity.class);
                        }
                }, 2, "用于获取通讯录数据功能", Manifest.permission.READ_CONTACTS);
                break;
            case R.id.su_bt2:
                //打开网页
                Uri uri = Uri.parse("http://www.baidu.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.su_bt3:
                //打开拨号界面
                Uri uri2 = Uri.parse("tel:10086");
                Intent intent2 = new Intent(Intent.ACTION_DIAL, uri2);
                startActivity(intent2);
                break;
            case R.id.su_bt4:
                //直接拨打电话
                checkPermission(new CheckPermListener() {
                    @Override
                    public void superPermission() {
                        Uri uri3 = Uri.parse("tel:10086");
                        Intent intent3 = new Intent(Intent.ACTION_CALL, uri3);
                        startActivity(intent3);
                    }
                }, 2, "用于打电话功能", Manifest.permission.CALL_PHONE);
                break;
            case R.id.su_bt5:
                String qq = "978515743";
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
                Uri uri4=Uri.parse(url);
                Intent intent4=new Intent(Intent.ACTION_VIEW,uri4);
                startActivity(intent4);
                break;
        }
    }
}
