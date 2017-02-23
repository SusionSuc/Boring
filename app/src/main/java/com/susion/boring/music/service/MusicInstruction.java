package com.susion.boring.music.service;

import android.content.Context;
import android.content.Intent;

import com.susion.boring.music.activity.PlayMusicActivity;
import com.susion.boring.music.model.Song;

/**
 * Created by susion on 17/2/13.
 */
public class MusicInstruction {

    //service
    public static final String SERVICE_RECEIVER_QUERY_CURRENT_STATE = "SERVICE_RECEIVER_QUERY_CURRENT_STATE";
    public static final String SERVICE_RECEIVER_PLAY_MUSIC = "SERVICE_RECEIVER_PLAY_MUSIC";
    public static final String SERVICE_RECEIVER_PAUSE_MUSIC = "SERVICE_RECEIVER_PAUSE_MUSIC";
    public static final String SERVICE_RECEIVER_SEEK_TO = "SERVICE_RECEIVER_SEEK_TO";
    public static final String SERVICE_PARAM_SEEK_TO_POS = "SERVICE_PARAM_SEEK_TO_POS";
    public static final String SERVICE_SAVE_LAST_PLAY_MUSIC = "SERVICE_SAVE_LAST_PLAY_MUSIC";
    public static final String SERVICE_CURRENT_PLAY_MUSIC = "SERVICE_CURRENT_PLAY_MUSIC";
    public static final String SERVICE_LOAD_MUSIC_INFO = "LOAD_MUSIC_INFO";
    public static final String SERVICE_PARAM_PLAY_SONG = "SERVICE_PARAM_PLAY_SONG";
    public static final String SERVICE_PARAM_IS_PLAY = "SERVICE_PARAM_IS_PLAY";
    public static final String SERVER_RECEIVER_CHANGE_MUSIC = "SERVER_RECEIVER_CHANGE_MUSIC";
    public static final String SERVICE_PARAM_CHANGE_MUSIC = "SERVICE_PARAM_CHANGE_MUSIC";
    public static final String SERVICE_RECEIVER_QUERY_IS_PLAYING = "SERVICE_RECEIVER_QUERY_IS_PLAYING";
    public static final String SERVICE_RECEIVER_GET_PLAY_PROGRESS = "SERVICE_RECEIVER_GET_PLAY_PROGRESS";
    public static final String SERVICE_PARAM_PLAY_SONG_AUTO_PLAY = "SERVICE_PARAM_PLAY_SONG_AUTO_PLAY";
    public static final String SERVER_RECEIVER_UPDATE_PLAY_MUSIC_INFO = "SERVER_RECEIVER_UPDATE_PLAY_MUSIC_INFO";
    public static final String SERVICE_PARAM_UPDATE_SONG = "SERVICE_PARAM_UPDATE_SONG";


    //client
    public static final String CLIENT_ACTION_MUSIC_INFO = "CLIENT_ACTION_MUSIC_INFO";
    public static final String CLIENT_RECEIVER_CURRENT_SERVER_STATE = "CLIENT_RECEIVER_CURRENT_SERVER_STATE";
    public static final String CLIENT_RECEIVER_PLAYER_PREPARED = "CLIENT_RECEIVER_PLAYER_PREPARED";
    public static final String CLIENT_RECEIVER_UPDATE_BUFFERED_PROGRESS = "CLIENT_RECEIVER_UPDATA_BUFFERED_PROGRESS";
    public static final String CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS = "CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS";
    public static final String CLIENT_PARAM_BUFFERED_PROGRESS = "CLIENT_PARAM_BUFFERED_PROGRESS";
    public static final String CLIENT_PARAM_PLAY_PROGRESS_CUR_POS = "CLIENT_PARAM_PLAY_PROGRESS_CUR_POS";
    public static final String CLIENT_PARAM_PLAY_PROGRESS_DURATION = "CLIENT_PARAM_PLAY_PROGRESS__DURATION";
    public static final String CLIENT_PARAM_PREPARED_TOTAL_DURATION = "CLIENT_PARAM_PREPARED_TOTAL_DURATION";
    public static final String CLIENT_RECEIVER_CURRENT_PLAY_MUSIC = "CLIENT_RECEIVER_CURRENT_PLAY_MUSIC";
    public static final String CLIENT_PARAM_CURRENT_PLAY_MUSIC = "CLIENT_PARAM_CURRENT_PLAY_MUSIC";
    public static final String CLIENT_PARAM_SERVER_STATE = "CLIENT_PARAM_SERVER_STATE";
    public static final String CLIENT_RECEIVER_SET_DURATION = "CLIENT_RECEIVER_SET_DURATION";
    public static final String CLIENT_PARAM_MEDIA_DURATION = "CLIENT_PARAM_MEDIA_DURATION";
    public static final String CLIENT_RECEIVER_CURRENT_IS_PALING = "CLIENT_RECEIVER_CURRENT_IS_PALING";
    public static final String CLIENT_PARAM_IS_PLAYING = "CLIENT_PARAM_IS_PLAYING";
    public static final String CLIENT_RECEIVER_CURRENT_PLAY_PROGRESS = "CLIENT_RECEIVER_CURRENT_PLAY_PROGRESS";
    public static final String CLIENT_PARAM_CURRENT_PLAY_PROGRESS = "CLIENT_PARAM_CURRENT_PLAY_PROGRESS";
    public static final String CLIENT_PARAM_NEED_LOAD_MUSIC = "CLIENT_PARAM_NEED_LOAD_MUSIC";

    public static void startMusicPlayService(Context ctx) {
        Intent intent = new Intent(ctx, MusicPlayerService.class);
        ctx.startService(intent);
    }
}
