package com.cxp.androidbase.scheme;

import android.widget.TextView;

import com.cxp.androidbase.BaseActivity;
import com.cxp.androidbase.R;

/**
 * 文 件 名: SchemeActivity
 * 创 建 人: CXP
 * 创建日期: 2019-09-03 14:37
 * 描    述: Scheme 协议
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class SchemeActivity extends BaseActivity {

    private TextView mTv;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_scheme);
        mTv = findViewById(R.id.scheme_tv);
        mTv.setText(String.format("name:%s。",getIntent().getStringExtra("name")));

    }
}
