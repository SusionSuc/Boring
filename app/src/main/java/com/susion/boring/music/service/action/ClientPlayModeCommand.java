package com.susion.boring.music.service.action;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.contract.MediaPlayerContract;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.music.service.MusicServiceInstruction;

/**
 * Created by susion on 17/3/2.
 */
public class ClientPlayModeCommand implements MediaPlayerContract.ClientPlayModeCommand {

    private Context mContext;

    public ClientPlayModeCommand(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void startCirclePlayMode() {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_PLAY_MODE_CIRCLE);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void startRandomPlayMode() {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_PLAY_MODE_RANDOM);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void circlePlayPlayList(PlayList mData) {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_CIRCLE_PLAY_PLAY_LIST);
        intent.putExtra(MusicServiceInstruction.SERVER_PARAM_PLAY_LIST, mData);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void randomPlayPlayList(PlayList mPlayList) {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_RANDOM_PLAY_PLAY_LIST);
        intent.putExtra(MusicServiceInstruction.SERVER_PARAM_PLAY_LIST, mPlayList);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void startQueuePlayMode() {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_PLAY_MODE_QUEUE);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void queryCurrentPlayMode() {
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_SONG_QUERY_CUR_MODE);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

}
