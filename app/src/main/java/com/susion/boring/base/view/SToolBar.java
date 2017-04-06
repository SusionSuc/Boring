package com.susion.boring.base.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.ui.mainui.FragmentFactory;

/**
 * Created by susion on 17/1/17.
 */
public class SToolBar extends RelativeLayout implements View.OnClickListener {

    public static final int HIDDEN_LEFT_ICON_RES = -1;

    private Context mContext;
    private ImageView mLeftIcon;
    private ImageView mRightIcon;
    private TextView mTvInteresting;
    private TextView mTvMusic;

    private int mCurrentSelectItem = 0;
    private boolean isMainPage = false;

    private OnItemClickListener listener;
    private OnRightIconClickListener rightIconClickListener;
    private TextView mTvTitle;

    public SToolBar(Context context) {
        super(context);
        init();
    }

    public SToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    public void setContext(Activity activity)
    {
        mContext = activity;
    }

    private void init() {
        mContext = getContext();
        View.inflate(mContext, R.layout.view_tool_bar, this);
        findView();
        setSelectedItem(mCurrentSelectItem);
        setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
    }

    private void findView() {
        mLeftIcon = (ImageView) findViewById(R.id.toolbar_left_icon);
        mRightIcon = (ImageView) findViewById(R.id.toolbar_right_icon);
        mTvTitle = (TextView) findViewById(R.id.toolbar_title);
        mTvMusic = (TextView) findViewById(R.id.toolbar_tv_music);
        mTvInteresting = (TextView) findViewById(R.id.toolbar_tv_interesting);

        mRightIcon.setOnClickListener(this);
        mLeftIcon.setOnClickListener(this);
        mTvMusic.setOnClickListener(this);
        mTvInteresting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int clickId = view.getId();
        switch (clickId) {
            case R.id.toolbar_left_icon:
                if (isMainPage) {
                    if (listener != null) {
                        listener.onMenuItemClick(view);
                    }
                } else {
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).finish();
                    }
                }
                break;
            case R.id.toolbar_tv_music:
                mCurrentSelectItem = FragmentFactory.ITEM_MUSIC;
                break;

            case R.id.toolbar_tv_interesting:
                mCurrentSelectItem = FragmentFactory.ITEM_INTERESTING;
                break;
            case R.id.toolbar_right_icon:
                if (rightIconClickListener != null) {
                    rightIconClickListener.onRightIconClick();
                }
        }

        setSelectedItem(mCurrentSelectItem);
        notifyListener(clickId, view);
    }

    private void notifyListener(int clickId, View view) {
        if (listener != null && isMainPage) {
            switch (clickId) {
                case R.id.toolbar_tv_music:
                    listener.onMusicItemClick(view);
                    break;

                case R.id.toolbar_tv_interesting:
                    listener.onInterestingItemClick(view);
                    break;
            }
        }
    }

    public void setSelectedItem(int selectedItem) {
        clearSelectItem();
        switch (selectedItem) {
            case FragmentFactory.ITEM_MUSIC:
                mTvMusic.setTextColor(getResources().getColor(R.color.white));
                break;
            case FragmentFactory.ITEM_INTERESTING:
                mTvInteresting.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    private void clearSelectItem() {
        mTvInteresting.setTextColor(getResources().getColor(R.color.black));
        mTvMusic.setTextColor(getResources().getColor(R.color.black));
    }

    public void setLeftIcon(int resId) {
        if (resId == HIDDEN_LEFT_ICON_RES) {
            mLeftIcon.setVisibility(INVISIBLE);
            return;
        }
        mLeftIcon.setVisibility(VISIBLE);
        mLeftIcon.setImageResource(resId);
    }

    public void setRightIcon(int resId) {
        mRightIcon.setVisibility(VISIBLE);
        mRightIcon.setImageResource(resId);
    }

    public int getCurrentSelectItem() {
        return mCurrentSelectItem;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setRightIconClickListener(OnRightIconClickListener rightIconClickListener) {
        this.rightIconClickListener = rightIconClickListener;
    }

    public void setMainPage(boolean mainPage) {
        isMainPage = mainPage;
    }

    public void setTitle(String title) {
        isMainPage = false;
        mTvTitle.setText(title);
        findViewById(R.id.toolbar_main_menu).setVisibility(GONE);
        mTvTitle.setVisibility(VISIBLE);
    }

    public void setTitleColorRes(int res) {
        mTvTitle.setTextColor(getResources().getColor(res));
    }

    public interface OnItemClickListener {
        void onMenuItemClick(View v);

        void onMusicItemClick(View v);

        void onInterestingItemClick(View v);
    }

    public interface OnRightIconClickListener {
        void onRightIconClick();
    }
}
