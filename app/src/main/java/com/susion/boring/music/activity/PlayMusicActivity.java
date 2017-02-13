package com.susion.boring.music.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.http.APIHelper;
import com.susion.boring.music.model.LyricResult;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.IPlayMusicPresenter;
import com.susion.boring.music.presenter.PlayMusicPresenter;
import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.music.service.MusicPlayerService;
import com.susion.boring.music.view.IMediaPlayView;
import com.susion.boring.music.view.LyricView;
import com.susion.boring.music.view.MediaSeekBar;
import com.susion.boring.music.view.MusicPlayControlView;
import com.susion.boring.utils.ImageUtils;
import com.susion.boring.utils.MediaUtils;
import com.susion.boring.view.SToolBar;

import java.io.Serializable;

import rx.Observer;

public class PlayMusicActivity extends BaseActivity implements IMediaPlayView{
    private static final String TO_PLAY_MUSIC_INFO = "played_music";

    private SToolBar mToolBar;
    private MediaSeekBar mSeekBar;
    private MusicPlayControlView mPlayControlView;
    private LinearLayout mLl;
    private IPlayMusicPresenter mPresenter;

    private Song mSong;
    private TextView mTvPlayedTime;
    private TextView mTvLeftTime;
    private LyricView mLyricView;

    public static void start(Context context, Song song) {
        Intent intent = new Intent();
        intent.putExtra(TO_PLAY_MUSIC_INFO, song);
        intent.setClass(context, PlayMusicActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_play_music;
    }

    @Override
    public void findView(){
        mToolBar = (SToolBar) findViewById(R.id.toolbar);
        mSeekBar = (MediaSeekBar) findViewById(R.id.seek_bar);
        mPlayControlView = (MusicPlayControlView) findViewById(R.id.control_view);
        mLl = (LinearLayout) findViewById(R.id.ll);
        mTvPlayedTime = (TextView) findViewById(R.id.tv_has_play_time);
        mTvLeftTime = (TextView) findViewById(R.id.tv_left_time);
        mLyricView = (LyricView) findViewById(R.id.lyric_view);
    }

    @Override
    public void initView() {
        mToolBar.setMainPage(false);
        mToolBar.setTitle(mSong.name);
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setBlurBackground();
        initListener();

        mPlayControlView.setIsPlay(true);

        try{
            mPresenter.initMediaPlayer(mSong.audio, mPlayControlView.ismIsPlay());
        }catch(Exception e){

        }
    }

    @Override
    public void initListener() {
        mPlayControlView.setOnControlItemClickListener(new MusicPlayControlView.MusicPlayerControlViewItemClickListener() {
            @Override
            public void onMoreItemClick() {

            }

            @Override
            public void onNextItemClick() {

            }

            @Override
            public void onPreItemClick() {

            }

            @Override
            public void onPatternItemClick() {

            }

            @Override
            public void onStartOrStartItemClick(boolean isPlay) {
                if (isPlay) {
                    mPlayControlView.setIsPlay(mPresenter.startPlay());
                } else {
                    mPresenter.pausePlay();
                }
            }
        });


        mSeekBar.setMediaSeekBarListener(new MediaSeekBar.MediaSeekBarListener() {
            @Override
            public void onStartDragThumb(int currentProgress) {
                mPresenter.pausePlay();
            }

            @Override
            public void onDraggingThumb(int currentProgress) {

            }

            @Override
            public void onStopDragThumb(int currentProgress) {
                if (!mPlayControlView.ismIsPlay()) {
                    mPlayControlView.setIsPlay(true);
                }
                mPresenter.startPlay();
                mPresenter.seekTo(currentProgress);
            }

            @Override
            public void onProgressChange(int currentProgress) {
                mPresenter.seekTo(currentProgress);
            }
        });
    }

    @Override
    public void initData() {
        mSong = (Song) getIntent().getSerializableExtra(TO_PLAY_MUSIC_INFO);
        mPresenter = new PlayMusicPresenter(this);
        loadLyric();

        //start service to play music
        Intent intent = new Intent(this, MusicPlayerService.class);
        intent.putExtra(MusicInstruction.CLIENT_ACTION_MUSIC_INFO, mSong);
        startService(intent);
    }

    private void loadLyric() {
        APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getMusicLyric(mSong.id), new Observer<LyricResult>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(LyricResult s) {
                mLyricView.setLyrics(s.lrc.lyric);
            }
        });

    }

    private void setBlurBackground() {
        ImageUtils.LoadImage(this, mSong.album.picUrl, new ImageUtils.OnLoadFinishLoadImage() {
            @Override
            public void loadImageFinish(String imageUri, View view, Bitmap loadedImage) {
                mLl.setBackground(mPresenter.getBackgroundBlurImage(loadedImage));
            }
        });
    }

    @Override
    public void preparedPlay(MediaPlayer player) {
        mSeekBar.setCurrentProgress(0);
        mSeekBar.setMaxProgress(player.getDuration());
    }

    @Override
    public void completionPlay() {
        mPlayControlView.setIsPlay(false);
        mSeekBar.setCurrentProgress(mSeekBar.getMaxProgress());
    }

    @Override
    public void updateBufferedProgress(int percent) {
        mSeekBar.setHasBufferProgress( (int) (percent * 1.0f / 100 * mSeekBar.getMaxProgress()));
    }

    @Override
    public void updatePlayProgress(int curPos, int left) {
        mTvPlayedTime.setText(MediaUtils.getDurationString(curPos, false));
        mTvLeftTime.setText(MediaUtils.getDurationString(left, true));
        mSeekBar.setCurrentProgress(curPos);
        mLyricView.setCurrentLyricByTime(MediaUtils.getDurationString(curPos, false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stopPlay();
        mPresenter.releaseResource();
    }


    //start background play
    @Override
    protected void onPause() {
        super.onPause();


    }
}
