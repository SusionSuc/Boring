package com.susion.boring.music.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.susion.boring.R;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.view.IPlayMusicView;
import com.susion.boring.view.SToolBar;

public class PlayMusicActivity extends Activity implements IPlayMusicView{
    private static final String TO_PLAY_MUSIC_INFO = "played_music";
    private SToolBar mToolBar;

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
        initView();
    }

    private void initView() {
        mToolBar = (SToolBar) findViewById(R.id.toolbar);
        mToolBar.setMainPage(false);
        mToolBar.setTitle("演员");
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
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
