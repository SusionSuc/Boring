package com.susion.boring.music.presenter.command;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.music.service.MusicInstruction;

/**
 * Created by susion on 17/3/3.
 */
public class ClientPlayQueueControlCommand implements MediaPlayerContract.ClientPlayQueueControlCommand{
    private Context mContext;

    public ClientPlayQueueControlCommand(Context c) {
        mContext = c;
    }

    @Override
    public void getPlayQueue() {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_GET_PLAY_QUEUE);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void removeSongFromQueue(Song song) {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_REMOVE_SONG_FROM_QUEUE);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_REMOVE_SONG, song);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void changeMusic(Song song) {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_CHANGE_MUSIC);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_CHANGE_MUSIC, song);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

    }
}
