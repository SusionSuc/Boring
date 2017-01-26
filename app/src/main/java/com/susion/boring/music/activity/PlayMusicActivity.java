package com.susion.boring.music.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.susion.boring.R;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.IPlayMusicPresenter;
import com.susion.boring.music.presenter.PlayMusicPresenter;
import com.susion.boring.music.view.IPlayMusicView;
import com.susion.boring.music.view.MediaSeekBar;
import com.susion.boring.music.view.MusicPlayControlView;
import com.susion.boring.music.view.WaterWaveRotateImageView;
import com.susion.boring.utils.StatusBarUtil;
import com.susion.boring.view.SToolBar;

public class PlayMusicActivity extends Activity implements IPlayMusicView{
    private static final String TO_PLAY_MUSIC_INFO = "played_music";
    private SToolBar mToolBar;
    private WaterWaveRotateImageView mIvWaterRote;
    private MediaSeekBar mSeekBar;
    private MusicPlayControlView mPlayControlView;
    private LinearLayout mLl;
    private IPlayMusicPresenter mPresenter;

    private Song mSong;

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
        mPresenter = new PlayMusicPresenter(this);
        findView();
        initData();
        initView();
    }

    private void findView(){
        mToolBar = (SToolBar) findViewById(R.id.toolbar);
        mIvWaterRote = (WaterWaveRotateImageView) findViewById(R.id.water_wave_rotate_iv);
        mSeekBar = (MediaSeekBar) findViewById(R.id.seek_bar);
        mPlayControlView = (MusicPlayControlView) findViewById(R.id.control_view);
        mLl = (LinearLayout) findViewById(R.id.ll);
    }

    private void initData() {
        mSong = (Song) getIntent().getSerializableExtra(TO_PLAY_MUSIC_INFO);
    }

    private void initView() {
        mToolBar.setMainPage(false);
        mToolBar.setTitle(mSong.name);
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));

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

            }
        });

        mPresenter.setBackground(mSong.album.picUrl, mLl, this);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }

    @Override
    public void initMusicInfoUI() {

    }

    @Override
    public void initProgressView() {

    }

    @Override
    public void updateProgress() {

    }

    @Override
    public void startMusic() {

    }

    @Override
    public void stopMusic() {

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
}
