package com.susion.boring.music.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.IMediaPlayPresenter;
import com.susion.boring.music.presenter.MediaPlayPresenter;

/**
 * Created by susion on 17/2/13.
 */
public class MusicPlayerService extends Service {

    private MusicReceiver mReceiver;
    private IMediaPlayPresenter mPresenter;


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initMusicInfo(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        mReceiver = new MusicReceiver();
        mPresenter = new MediaPlayPresenter(null, this);
    }

    private void initMusicInfo(Intent intent) {
        Song song = (Song) intent.getSerializableExtra(MusicInstruction.CLIENT_ACTION_MUSIC_INFO);
        
        try {
            mPresenter.initMediaPlayer(song.audio, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MusicReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
        }
    }

}
