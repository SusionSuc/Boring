package com.susion.boring.music.service.action;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.music.mvp.contract.MediaPlayerContract;
import com.susion.boring.music.service.MusicServiceInstruction;

/**
 * Created by susion on 17/3/2.
 */
public class ClientPlayControlCommand implements MediaPlayerContract.ClientPlayControlCommand {

    private Context mContext;

    public ClientPlayControlCommand(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void changeToNextMusic() {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_PLAY_NEXT);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void changeToPreMusic() {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_PLAY_PRE);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void getCurrentPlayMusic() {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_CURRENT_PLAY_MUSIC);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void pausePlay() {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_PAUSE_MUSIC);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void play() {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_PLAY_MUSIC);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void seekProgressTo(int currentProgress) {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_SEEK_TO);
        intent.putExtra(MusicServiceInstruction.SERVER_PARAM_SEEK_TO_POS, currentProgress);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }
}
