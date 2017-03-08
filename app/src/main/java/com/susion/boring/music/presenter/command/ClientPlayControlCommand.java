package com.susion.boring.music.presenter.command;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.utils.BroadcastUtils;

/**
 * Created by susion on 17/3/2.
 */
public class ClientPlayControlCommand implements MediaPlayerContract.ClientPlayControlCommand {

    private Context mContext;

    public ClientPlayControlCommand(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void queryServiceIsPlaying() {
        BroadcastUtils.sendIntentAction(mContext, MusicInstruction.SERVICE_RECEIVER_QUERY_IS_PLAYING);
    }

    @Override
    public void tryToChangePlayingMusic(Song song) {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_CHANGE_MUSIC);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_CHANGE_MUSIC, song);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void changeToNextMusic() {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_PLAY_NEXT);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void changeToPreMusic() {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_PLAY_PRE);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void loadMusicInfoToService(Song song, boolean autoPlay) {
        Intent intent = new Intent(MusicInstruction.SERVICE_LOAD_MUSIC_INFO);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_PLAY_SONG, song);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_PLAY_SONG_AUTO_PLAY, autoPlay);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void getCurrentPlayMusic() {
        Intent intent = new Intent(MusicInstruction.SERVICE_CURRENT_PLAY_MUSIC);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void pausePlay() {
        Intent intent = new Intent(MusicInstruction.SERVICE_RECEIVER_PAUSE_MUSIC);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void updatePlayMusic(Song song) {
        Intent intent = new Intent(MusicInstruction.SERVICE_PARAM_UPDATE_SONG);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

}
