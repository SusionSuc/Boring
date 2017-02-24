package com.susion.boring.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.susion.boring.R;
import com.susion.boring.music.model.DownTask;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.FileDownloadPresenter;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.utils.ToastUtils;

/**
 * Created by susion on 17/2/21.
 */
public class PlayOperatorView  extends LinearLayout implements View.OnClickListener{

    private Context mContext;
    private ImageView mIvCircle;
    private ImageView mIvRandom;
    private ImageView mIvLike;
    private ImageView mIvNextPlay;

    private Song mSong;
    private MediaPlayerContract.PlayMusicCommunicatePresenter mCommunicatePresenter;
    private MediaPlayerContract.PlayMusicControlPresenter mPresenter;

    public PlayOperatorView(Context context) {
        super(context);
        init();
    }

    public PlayOperatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mContext = getContext();
        View.inflate(mContext, R.layout.view_play_operator, this);
        findView();
        initView();
    }

    private void findView() {
        mIvCircle = (ImageView) findViewById(R.id.view_play_operator_iv_circle);
        mIvRandom = (ImageView) findViewById(R.id.view_play_operator_iv_random);
        mIvNextPlay = (ImageView) findViewById(R.id.view_play_operator_iv_next_play);
        mIvLike = (ImageView) findViewById(R.id.view_play_operator_iv_like);
    }
    private void initView() {
        mIvCircle.setOnClickListener(this);
        mIvNextPlay.setOnClickListener(this);
        mIvRandom.setOnClickListener(this);
        mIvLike.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_play_operator_iv_circle :
                break;

            case R.id.view_play_operator_iv_random :
                break;

            case R.id.view_play_operator_iv_next_play :
                mPresenter.nextPlay(mSong);
                break;

            case R.id.view_play_operator_iv_like :
                mSong.favorite = !mSong.favorite;
                mCommunicatePresenter.likeMusic(mSong);
                refreshLikeStatus();
                break;
        }
    }

    public void setSong(Song song) {
        this.mSong = song;
        refreshLikeStatus();
    }

    private void refreshLikeStatus() {
        if (mSong.favorite) {
            mIvLike.setImageResource(R.mipmap.play_operator_un_love);
        } else {
            mIvLike.setImageResource(R.mipmap.play_operator_love);
        }
    }

    public void setPresenter(MediaPlayerContract.PlayMusicControlPresenter presenter, MediaPlayerContract.PlayMusicCommunicatePresenter communicatePresenter) {
        mPresenter = presenter;
        mCommunicatePresenter = communicatePresenter;
    }
}
