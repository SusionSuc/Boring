package com.susion.boring.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.susion.boring.R;
import com.susion.boring.utils.StatusBarUtil;
import com.susion.boring.view.SToolBar;

/**
 * Created by susion on 17/1/19.
 */
public abstract  class BaseActivity extends AppCompatActivity {

    public SToolBar mToolBar;

    @LayoutRes
    public  abstract int getLayoutId();

    public abstract void findView();

    public abstract void initView();

    public  abstract void initListener();

    public abstract void initData();

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setStatusBar();
        initTransitionAnim();
        mToolBar = (SToolBar) findViewById(R.id.toolbar);
        findView();
        initView();
        initListener();
        initData();
    }

    public void initTransitionAnim() {
    }

}
