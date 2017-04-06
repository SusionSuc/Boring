package com.susion.boring.read.mvp.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by susion on 17/3/15.
 */
public class NestChildViewPager extends ViewPager {

    PointF mDownPoint;
    PointF mCurPoint;

    public NestChildViewPager(Context context) {
        super(context);
    }

    public NestChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDownPoint = new PointF();
        mCurPoint = new PointF();
    }

}
