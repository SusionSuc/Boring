package com.susion.boring.base.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.view.swipebacklayout.SwipeBackActivity;
import com.susion.boring.base.view.swipebacklayout.SwipeBackLayout;
import com.susion.boring.base.view.SToolBar;

/**
 * Created by susion on 17/1/19.
 */
public abstract class BaseActivity extends SwipeBackActivity {

    public SToolBar mToolBar;
    private SwipeBackLayout mSwipeBackLayout;

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void findView();

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    protected void initParamsAndPresenter() {
    }

    protected void setStatusBar() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        initSwipeBackLayout();
        setContentView(getLayoutId());
        setStatusBar();
        initTransitionAnim();
        View view = findViewById(R.id.toolbar);
        if (view != null && view instanceof SToolBar) {
            mToolBar = (SToolBar) view;
            mToolBar.setLeftIcon(R.mipmap.ic_back);
        }

        initParamsAndPresenter();
        findView();
        initView();
        initListener();
        initData();
    }

    public void initTransitionAnim() {

    }

    @SuppressWarnings("deprecation")
    public void initSwipeBackLayout() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    public void setEdgeTrackingEnabled(int size, int position) {
        if (size == 0) {
        }
        // 只有一个fragment  - 左右滑关闭
        else if (size == 1 && position == 0) {
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
        }
        // 多个fragment  - 位于左侧尽头 - 只可左滑关闭
        else if (size != 1 && position == 0) {
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        }
        // 多个fragment  - 位于右侧尽头 - 只可右滑关闭
        else if (size != 1 && position == size - 1) {
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
        }
        // 多个fragment  - 位于中间 - 屏蔽所有左右滑关闭事件
        else {
            mSwipeBackLayout.setEdgeTrackingEnabled(0);
        }
    }

}
