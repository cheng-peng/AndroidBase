package com.cxp.androidbase.content_provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文 件 名: ReadContactsActivity
 * 创 建 人: CXP
 * 创建日期: 2019-08-30 14:19
 * 描    述: 获取通讯录数据
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class ReadContactsActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private Context mContext;
    private List<Map<String, String>> mDatas;
    private ReadContactsAdapter mAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_read_contacts);
        mRecyclerView = findViewById(R.id.rc_recyclerview);

        mContext = this;
        mDatas = new ArrayList<>();

        //初始化数据
        initDatas();

        mAdapter = new ReadContactsAdapter(mContext, mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initDatas() {

        Cursor cursor1 = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        }, null, null, "sort_key COLLATE LOCALIZED asc");

        if (cursor1 != null) {
            while (cursor1.moveToNext()) {
                //id
                int id = cursor1.getInt(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                //用户名
                String name = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                }, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);

                String phone = "";
                while (cursor2.moveToNext()) {
                    phone += "/" + cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                cursor2.close();

                Map<String, String> map = new HashMap<>();
                map.put("title", name);
                map.put("content", phone.substring(1));
                mDatas.add(map);
            }
            cursor1.close();
        }
    }
}
