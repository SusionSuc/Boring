package com.susion.boring.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.susion.boring.R;
import com.susion.boring.utils.UIUtils;

/**
 * Created by susion on 17/1/23.
 */
public class MusicPlayControlView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private ImageView mIvStartStop;
    private ImageView mIvNext;
    private ImageView mIvPre;
    private ImageView mIvLoading;

    private boolean mIsPlay = false;
    private MusicPlayerControlViewItemClickListener listener;

    public MusicPlayControlView(Context context) {
        super(context);
        init(context);
    }

    public MusicPlayControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MusicPlayControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View.inflate(mContext, R.layout.view_music_play_control, this);
        findView();

        mIsPlay = false;
    }

    private void findView() {
        mIvStartStop = (ImageView) findViewById(R.id.view_music_player_start_or_stop);
        mIvNext = (ImageView) findViewById(R.id.view_music_player_next);
        mIvPre = (ImageView) findViewById(R.id.view_music_player_pre);
        mIvLoading = (ImageView) findViewById(R.id.view_music_player_iv_loading);

        mIvStartStop.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
        mIvPre.setOnClickListener(this);
    }


    public boolean ismIsPlay() {
        return mIsPlay;
    }

    public void setIsPlay(boolean mIsPlay) {
        this.mIsPlay = mIsPlay;
        setPlayBtn();
    }

    private void setPlayBtn() {
        if (mIsPlay) {
            mIvStartStop.setImageResource(R.mipmap.ic_music_player_play);
        } else {
            mIvStartStop.setImageResource(R.mipmap.ic_music_player_stop);
        }
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.view_music_player_next:

                break;
            case R.id.view_music_player_pre:

                break;
            case R.id.view_music_player_start_or_stop:
                mIsPlay = !mIsPlay;
                setPlayBtn();
                break;
        }

        notifyListener(id);
    }

    private void notifyListener(int id) {
        if (listener == null) {
            return;
        }
        switch (id) {
            case R.id.view_music_player_next:
                listener.onNextItemClick();
                break;
            case R.id.view_music_player_pre:
                listener.onPreItemClick();
                break;
            case R.id.view_music_player_start_or_stop:
                listener.onStartOrStartItemClick(mIsPlay);
                break;
        }
    }


    public void startLoadingAnimation() {
        mIvLoading.setVisibility(VISIBLE);
        mIvStartStop.setVisibility(INVISIBLE);
        UIUtils.startSimpleRotateAnimation(mIvLoading);
    }

    public void endLoadingAnimationAndPlay() {
        mIvLoading.setVisibility(INVISIBLE);
        mIvStartStop.setVisibility(VISIBLE);
        mIvLoading.clearAnimation();
    }

    public interface MusicPlayerControlViewItemClickListener {
        void onNextItemClick();

        void onPreItemClick();

        void onStartOrStartItemClick(boolean isPlay);
    }

    public void setOnControlItemClickListener(MusicPlayerControlViewItemClickListener listener) {
        this.listener = listener;
    }
}
