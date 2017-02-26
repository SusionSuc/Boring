package com.susion.boring.music.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.MusicServiceContract;
import com.susion.boring.music.service.MusicInstruction;

/**
 * Created by susion on 17/2/24.
 *
 * Service Receiver
 */
public class ServiceReceiverPresenter implements MusicServiceContract.ReceiverPresenter{

    private final String TAG = getClass().getSimpleName();
    private ServiceMusicReceiver mReceiver;
    private MusicServiceContract.Service mService;

    public ServiceReceiverPresenter(MusicServiceContract.Service view) {
        mService = view;
        mReceiver = new ServiceMusicReceiver();
        LocalBroadcastManager.getInstance(mService.getContext()).registerReceiver(mReceiver, mReceiver.getIntentFilter());
    }

    @Override
    public void releaseResource() {
        mService.getContext().unregisterReceiver(mReceiver);
    }

    class ServiceMusicReceiver extends BroadcastReceiver {
        IntentFilter getIntentFilter(){
            IntentFilter filter = new IntentFilter();
            filter.addAction(MusicInstruction.SERVICE_RECEIVER_PLAY_MUSIC);
            filter.addAction(MusicInstruction.SERVICE_RECEIVER_PAUSE_MUSIC);
            filter.addAction(MusicInstruction.SERVICE_RECEIVER_SEEK_TO);
            filter.addAction(MusicInstruction.SERVICE_SAVE_LAST_PLAY_MUSIC);
            filter.addAction(MusicInstruction.SERVICE_CURRENT_PLAY_MUSIC);
            filter.addAction(MusicInstruction.SERVICE_LOAD_MUSIC_INFO);
            filter.addAction(MusicInstruction.SERVER_RECEIVER_CHANGE_MUSIC);
            filter.addAction(MusicInstruction.SERVICE_RECEIVER_QUERY_CURRENT_STATE);
            filter.addAction(MusicInstruction.SERVICE_RECEIVER_QUERY_IS_PLAYING);
            filter.addAction(MusicInstruction.SERVICE_RECEIVER_GET_PLAY_PROGRESS);
            filter.addAction(MusicInstruction.SERVICE_RECEIVER_GET_PLAY_PROGRESS);
            filter.addAction(MusicInstruction.SERVER_RECEIVER_UPDATE_PLAY_MUSIC_INFO);
            filter.addAction(MusicInstruction.SERVER_RECEIVER_PLAY_NEXT);
            filter.addAction(MusicInstruction.SERVER_RECEIVER_PLAY_PRE);
            filter.addAction(MusicInstruction.SERVER_RECEIVER_PLAY_MODE_CIRCLE);
            filter.addAction(MusicInstruction.SERVER_RECEIVER_PLAY_MODE_RANDOM);
            filter.addAction(MusicInstruction.SERVER_RECEIVER_SONG_TO_NEXT_PLAY);
            return filter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case MusicInstruction.SERVICE_LOAD_MUSIC_INFO:
                    mService.loadMusicInfo((Song) intent.getSerializableExtra(MusicInstruction.SERVICE_PARAM_PLAY_SONG),
                            intent.getBooleanExtra(MusicInstruction.SERVICE_PARAM_PLAY_SONG_AUTO_PLAY, false));
                    break;
                case MusicInstruction.SERVICE_RECEIVER_PLAY_MUSIC:
                    Log.e(TAG, "mService.playMusic()");
                    mService.playMusic();
                    break;
                case MusicInstruction.SERVICE_RECEIVER_PAUSE_MUSIC:
                    mService.pausePlay();
                    break;
                case MusicInstruction.SERVICE_RECEIVER_SEEK_TO:
                    mService.seekTo(intent);
                    break;
                case MusicInstruction.SERVICE_SAVE_LAST_PLAY_MUSIC:
                    mService.saveLastPlayMusic();
                    break;
                case MusicInstruction.SERVICE_CURRENT_PLAY_MUSIC:
                    mService.informCurrentPlayMusic();
                    break;
                case MusicInstruction.SERVER_RECEIVER_CHANGE_MUSIC:
                    mService.tryToChangeMusic((Song) intent.getSerializableExtra(MusicInstruction.SERVICE_PARAM_CHANGE_MUSIC));
                    break;
                case MusicInstruction.SERVICE_RECEIVER_QUERY_IS_PLAYING:
                    mService.informCurrentIfPlaying();
                    break;
                case MusicInstruction.SERVICE_RECEIVER_GET_PLAY_PROGRESS:
                    mService.informCurrentIfPlayProgress();
                    break;
                case MusicInstruction.SERVER_RECEIVER_UPDATE_PLAY_MUSIC_INFO:
                    mService.updateSong((Song) intent.getSerializableExtra(MusicInstruction.SERVICE_PARAM_UPDATE_SONG));
                    break;
                case MusicInstruction.SERVER_RECEIVER_PLAY_NEXT:
                    mService.playNextMusic();
                    break;
                case MusicInstruction.SERVER_RECEIVER_PLAY_PRE:
                    mService.PlayPreMusic();
                    break;
                case MusicInstruction.SERVER_RECEIVER_PLAY_MODE_CIRCLE:
                    mService.startCircleMode();
                    break;
                case MusicInstruction.SERVER_RECEIVER_PLAY_MODE_RANDOM:
                    mService.startRandomMode();
                    break;
                case MusicInstruction.SERVER_RECEIVER_SONG_TO_NEXT_PLAY:
                    mService.songToNextPlay((Song)intent.getSerializableExtra(MusicInstruction.SERVICE_PARAM_SONG_TO_NEXT_PLAY));
                    break;
            }
        }
    }


}
