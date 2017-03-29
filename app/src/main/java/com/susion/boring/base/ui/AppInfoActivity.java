package com.susion.boring.base.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.utils.SystemOperationUtils;

public class AppInfoActivity extends BaseActivity {

    private TextView mTvVersion;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AppInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info;
    }

    @Override
    public void findView() {
        mTvVersion = (TextView) findViewById(R.id.ac_app_info_tv_version);
    }

    @Override
    public void initView() {
        mToolBar.setTitle("关于随心");
        mTvVersion.setText("版本 v" + SystemOperationUtils.getAppVersion(this));

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


}
