package com.susion.boring.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.susion.boring.R;

/**
 * Created by susion on 17/1/17.
 */
public class SToolBar extends RelativeLayout implements View.OnClickListener, MainUIFragmentIndex{

    private Context mContext;
    private ImageView mLeftIcon;
    private ImageView mRightIcon;
    private ImageView mInteresting;
    private ImageView mPlayer;
    private ImageView mMusic;

    private int mCurrentSelectItem = 0;

    private boolean isMainPage = true;
    private String title;

    private OnItemClickListener listener;
    private OnRightIconClickListener rightIconClickListener;
    private TextView mTvTitle;

    public SToolBar(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public SToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public SToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(mContext, R.layout.view_tool_bar, this);
        findView();
        setSelectedItem(mCurrentSelectItem);
        mLeftIcon.setSelected(true);
        setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        setAttrs(attrs);
    }

    private void setAttrs(AttributeSet attrs) {
        if(attrs != null){
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SToolBar);
            isMainPage = !ta.getBoolean(R.styleable.SToolBar_showTitle, false);
            isShowTitle();
            ta.recycle();
        }

    }

    private void isShowTitle() {
        if (isMainPage) {
            findViewById(R.id.toolbar_main_menu).setVisibility(VISIBLE);
            mTvTitle.setVisibility(GONE);
        } else {
            findViewById(R.id.toolbar_main_menu).setVisibility(GONE);
            mTvTitle.setVisibility(VISIBLE);
        }
    }

    private void findView() {
        mLeftIcon = (ImageView) findViewById(R.id.toolbar_left_icon);
        mRightIcon = (ImageView) findViewById(R.id.toolbar_right_icon);
        mTvTitle = (TextView) findViewById(R.id.toolbar_title);
        mMusic = (ImageView) findViewById(R.id.toolbar_music);
        mPlayer = (ImageView) findViewById(R.id.toolbar_player);
        mInteresting = (ImageView) findViewById(R.id.toolbar_interesting);

        mRightIcon.setOnClickListener(this);
        mLeftIcon.setOnClickListener(this);
        mMusic.setOnClickListener(this);
        mPlayer.setOnClickListener(this);
        mInteresting.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        int clickId = view.getId();

        switch (clickId){
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
            case R.id.toolbar_music:
                mCurrentSelectItem = ITEM_MUSIC;
                break;
            case R.id.toolbar_player:
                mCurrentSelectItem = ITEM_PLAYER;
                break;
            case R.id.toolbar_interesting:
                mCurrentSelectItem = ITEM_INTERESTING;
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
        if (listener != null) {
            switch (clickId){
                case R.id.toolbar_music:
                    listener.onMusicItemClick(view);
                    break;
                case R.id.toolbar_player:
                    listener.onPlayerItemClick(view);
                    break;
                case R.id.toolbar_interesting:
                    listener.onInterestingItemClick(view);
                    break;
            }
        }
    }

    public void setSelectedItem(int selectedItem) {
        clearSelectItem();
        switch (selectedItem){
            case ITEM_MUSIC:
                mMusic.setSelected(true);
                break;
            case ITEM_PLAYER:
                mPlayer.setSelected(true);
                break;
            case ITEM_INTERESTING:
                mInteresting.setSelected(true);
                break;
        }
    }


    private void clearSelectItem() {
        mMusic.setSelected(false);
        mInteresting.setSelected(false);
        mPlayer.setSelected(false);
    }

    public void setLeftIcon(int resId){
        mLeftIcon.setVisibility(VISIBLE);
        mLeftIcon.setImageResource(resId);
    }

    public void setRightIcon(int resId){
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
        isShowTitle();
    }

    public void setTitle(String title) {
        this.title = title;
        mTvTitle.setText(title);
    }

    public interface OnItemClickListener{
        void onMenuItemClick(View v);
        void onMusicItemClick(View v);
        void onPlayerItemClick(View v);
        void onInterestingItemClick(View v);
    }

    public interface OnRightIconClickListener{
        void onRightIconClick();
    }
}
