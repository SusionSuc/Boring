package com.susion.boring.base.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.utils.UIUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by susion on 17/2/9.
 */
public class LoadMoreView extends TextView {

    private Context mContext;
    public static final String STR_LOADING = "正在加载更多...";
    public static final String STR_LOAD_FAILED = "加载失败";
    public static final String NO_MORE_DATA = "没有更多的数据了 -_-";

    public static final int LOADING = 1;
    public static final int LOAD_FAILED = 2;
    public static final int NO_LOAD = 3;
    public static final int NO_DATA = 4;

    private int mStatus = LOADING;


    public LoadMoreView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dp2Px(30));
        setLayoutParams(params);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setTextColor(getResources().getColor(R.color.colorPrimary));
        setTextSize(15);
        setTypeface(Typeface.DEFAULT_BOLD);
        setPadding(getPaddingLeft(), getPaddingTop() + 10, getPaddingRight(), getPaddingBottom() + 10);
//        setBackground(getResources().getDrawable(R.drawable.bg_view_load_more));

        switch (mStatus) {
            case LOADING:
                setVisibility(VISIBLE);
                setText(STR_LOADING);
                break;

            case LOAD_FAILED:
                setVisibility(VISIBLE);
                setText(STR_LOAD_FAILED);
                break;
            case NO_DATA:
                setVisibility(VISIBLE);
                setText(NO_MORE_DATA);
                break;

            case NO_LOAD:
                setVisibility(GONE);
                break;
        }
    }


    public void setLoadStatus(int status) {
        mStatus = status;
        switch (status) {
            case LOADING:
                setVisibility(VISIBLE);
                setText(STR_LOADING);
                break;

            case LOAD_FAILED:
                setVisibility(VISIBLE);
                setText(STR_LOAD_FAILED);
                break;
            case NO_DATA:
                setVisibility(VISIBLE);
                setText(NO_MORE_DATA);
                break;
            case NO_LOAD:
                setVisibility(GONE);
                break;

        }
    }
}
