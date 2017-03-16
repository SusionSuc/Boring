package com.susion.boring.base.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

/**
 * Created by susion on 17/2/10.
 * must be have to direct child view
 */
public class NestScrollView extends LinearLayout implements NestedScrollingParent {

    private Context mContext;
    private View mTopView;
    private View mSecondView;

    private int mTopViewHeight;
    private OverScroller mScroller;


    public NestScrollView(Context context) {
        super(context);
        init();
    }


    public NestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mContext = getContext();
        mScroller = new OverScroller(mContext);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 1) {
            mTopView = getChildAt(0);
            mSecondView = getChildAt(1);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTopView.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = mSecondView.getLayoutParams();
                params.height = getMeasuredHeight();  //占满整个屏幕
            }
        });

        setMeasuredDimension(getMeasuredWidth(), mTopView.getMeasuredHeight() + mSecondView.getMeasuredHeight());
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;   //只要是竖直滑动, 就拦截
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);
        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }

    }


    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //down - //up+
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y)   //call by scrollTo
    {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset())  //for fling
        {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }


}
