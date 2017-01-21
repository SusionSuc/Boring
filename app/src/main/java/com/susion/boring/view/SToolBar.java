package com.susion.boring.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.susion.boring.R;

/**
 * Created by susion on 17/1/17.
 */
public class SToolBar extends LinearLayout implements View.OnClickListener{

    private Context mContext;
    private ImageView mMenu;
    private ImageView mInteresting;
    private ImageView mPlayer;
    private ImageView mMusic;

    private OnItemClickListener listener;

    private int mCurrentSelectItem = 1;
    public static final int ITEM_MUSIC = 1;
    public static final int ITEM_PLAYER = 2;
    public static final int ITEM_INTERESTING = 3;

    public SToolBar(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public SToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        View.inflate(mContext, R.layout.view_tool_bar, this);
        findView();
        setSelectedItem(mCurrentSelectItem);
        mMenu.setSelected(true);
    }

    private void findView() {
        mMenu = (ImageView) findViewById(R.id.toolbar_menu);
        mMusic = (ImageView) findViewById(R.id.toolbar_music);
        mPlayer = (ImageView) findViewById(R.id.toolbar_player);
        mInteresting = (ImageView) findViewById(R.id.toolbar_interesting);

        mMenu.setOnClickListener(this);
        mMusic.setOnClickListener(this);
        mPlayer.setOnClickListener(this);
        mInteresting.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        int clickId = view.getId();

        switch (clickId){
            case R.id.toolbar_menu:
                if (listener != null) {
                    listener.onMenuItemClick(view);
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

    public int getmCurrentSelectItem() {
        return mCurrentSelectItem;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onMenuItemClick(View v);
        void onMusicItemClick(View v);
        void onPlayerItemClick(View v);
        void onInterestingItemClick(View v);
    }

}
