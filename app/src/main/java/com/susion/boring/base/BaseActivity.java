package com.susion.boring.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.susion.boring.R;
import com.susion.boring.base.view.swipebacklayout.SwipeBackActivity;
import com.susion.boring.base.view.swipebacklayout.SwipeBackLayout;
import com.susion.boring.utils.StatusBarUtil;
import com.susion.boring.view.SToolBar;

/**
 * Created by susion on 17/1/19.
 */
public abstract  class BaseActivity extends SwipeBackActivity {

    public SToolBar mToolBar;
    private SwipeBackLayout mSwipeBackLayout;

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
        initSwipeBackLayout();
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
