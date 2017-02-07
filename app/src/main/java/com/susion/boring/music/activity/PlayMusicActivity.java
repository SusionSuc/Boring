package com.susion.boring.music.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.IMediaPlayPresenter;
import com.susion.boring.music.presenter.MediaPlayPresenter;
import com.susion.boring.music.view.IMediaPlayView;
import com.susion.boring.music.view.MediaSeekBar;
import com.susion.boring.music.view.MusicPlayControlView;
import com.susion.boring.utils.FastBlurUtil;
import com.susion.boring.utils.ImageUtils;
import com.susion.boring.utils.MediaUtils;
import com.susion.boring.view.SToolBar;

public class PlayMusicActivity extends Activity implements IMediaPlayView{
    private static final String TO_PLAY_MUSIC_INFO = "played_music";

    private SToolBar mToolBar;
    private MediaSeekBar mSeekBar;
    private MusicPlayControlView mPlayControlView;
    private LinearLayout mLl;
    private IMediaPlayPresenter mPresenter;

    private Song mSong;
    private TextView mTvPlayedTime;
    private TextView mTvLeftTime;

    public static void start(Context context, Song song) {
        Intent intent = new Intent();
        intent.putExtra(TO_PLAY_MUSIC_INFO, song);
        intent.setClass(context, PlayMusicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        mPresenter = new MediaPlayPresenter(this);
        findView();
        initData();
        initView();
    }

    private void findView(){
        mToolBar = (SToolBar) findViewById(R.id.toolbar);
        mSeekBar = (MediaSeekBar) findViewById(R.id.seek_bar);
        mPlayControlView = (MusicPlayControlView) findViewById(R.id.control_view);
        mLl = (LinearLayout) findViewById(R.id.ll);
        mTvPlayedTime = (TextView) findViewById(R.id.tv_has_play_time);
        mTvLeftTime = (TextView) findViewById(R.id.tv_left_time);
    }

    private void initData() {
        mSong = (Song) getIntent().getSerializableExtra(TO_PLAY_MUSIC_INFO);
    }

    private void initView() {
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

    private void setBlurBackground() {
        ImageUtils.LoadImage(this, mSong.album.picUrl, new ImageUtils.OnLoadFinishLoadImage() {
            @Override
            public void loadImageFinish(String imageUri, View view, Bitmap loadedImage) {
                loadedImage = loadedImage.copy(loadedImage.getConfig(), true);
                mLl.setBackground(new BitmapDrawable(FastBlurUtil.doBlur(loadedImage, 200, true)));
            }

        });
    }


    private void initListener() {
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
    public void updatePlayProgress(int curPos, int duration) {
        mTvPlayedTime.setText(MediaUtils.getDurationString(curPos, false));
        mTvLeftTime.setText(MediaUtils.getDurationString(duration-curPos, true));
        mSeekBar.setCurrentProgress(curPos);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stopPlay();
        mPresenter.releaseResource();
    }


    @Override
    public void initMusicInfoUI() {

    }

    @Override
    public void startMusicPlayAnimation() {

    }

    @Override
    public void stopMusicPlayAnimation() {

    }

    @Override
    public void changPlayModule() {

    }

    @Override
    public void initMediaProgress(String duration) {

    }


}
