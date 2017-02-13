package com.susion.boring.music.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by susion on 17/2/13.
 */
public class MusicPlayerService extends Service {

    private MediaPlayer mMediaPlayer;
    private MusicReceiver mReceiver;


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
        mMediaPlayer = new MediaPlayer();
        mReceiver = new MusicReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicInstruction.BR_ACTION_MUSIC_PLAY);
        intentFilter.addAction(MusicInstruction.BR_ACTION_MUSIC_PAUSE);
        intentFilter.addAction(MusicInstruction.BR_ACTION_MUSIC_NEXT);
        intentFilter.addAction(MusicInstruction.BR_ACTION_MUSIC_LAST);
        intentFilter.addAction(MusicInstruction.BR_ACTION_MUSIC_SEEK_TO);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, intentFilter);
    }

    private void initMusicInfo(Intent intent) {

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
            if (action.equals(MusicInstruction.BR_ACTION_MUSIC_PLAY)) {
                play();
            } else if (action.equals(MusicInstruction.BR_ACTION_MUSIC_PAUSE)) {
                pause();
            } else if (action.equals(MusicInstruction.BR_ACTION_MUSIC_LAST)) {
                last();
            } else if (action.equals(MusicInstruction.BR_ACTION_MUSIC_NEXT)) {
                next();
            } else if (action.equals(MusicInstruction.BR_ACTION_MUSIC_SEEK_TO)) {
                seekTo(intent);
            }
        }
    }

    private void play() {

    }

    private void pause() {

    }

    private void last() {

    }

    private void next() {

    }

    private void seekTo(Intent intent) {

    }
}
