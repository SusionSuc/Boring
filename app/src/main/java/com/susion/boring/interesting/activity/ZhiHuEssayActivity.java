package com.susion.boring.interesting.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;

public class ZhiHuEssayActivity extends BaseActivity {

    private static int mEssayId;
    private TextView mTvContent;

    public static void start(Context mContext, int id) {
        mEssayId = id;
        Intent intent = new Intent(mContext, ZhiHuEssayActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhi_hu_essay;
    }

    @Override
    public void findView() {
        mTvContent = (TextView) findViewById(R.id.ac_zhi_hu_essay_tv_content);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {


    }


}
