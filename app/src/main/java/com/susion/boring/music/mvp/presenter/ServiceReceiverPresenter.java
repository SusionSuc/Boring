package com.susion.boring.music.mvp.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.base.service.BaseServiceContract;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.music.mvp.contract.MusicServiceContract;
import com.susion.boring.music.service.MusicServiceInstruction;

/**
 * Created by susion on 17/2/24.
 * Service Receiver
 */
public class ServiceReceiverPresenter implements BaseServiceContract.ReceiverPresenter {

    private ServiceMusicReceiver mReceiver;
    private MusicServiceContract.Service mService;

    public ServiceReceiverPresenter(MusicServiceContract.Service service) {
        mService = service;
        mReceiver = new ServiceMusicReceiver();
        LocalBroadcastManager.getInstance(mService.getContext()).registerReceiver(mReceiver, mReceiver.getIntentFilter());
    }

    @Override
    public void releaseResource() {
        LocalBroadcastManager.getInstance(mService.getContext()).unregisterReceiver(mReceiver);
    }

    class ServiceMusicReceiver extends BroadcastReceiver {
        IntentFilter getIntentFilter() {
            IntentFilter filter = new IntentFilter();
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_PLAY_MUSIC);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_PAUSE_MUSIC);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_SEEK_TO);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_SAVE_LAST_PLAY_MUSIC);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_CURRENT_PLAY_MUSIC);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_LOAD_MUSIC_INFO);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_CHANGE_MUSIC);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_QUERY_CURRENT_STATE);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_QUERY_IS_PLAYING);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_GET_PLAY_PROGRESS);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_GET_PLAY_PROGRESS);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_UPDATE_PLAY_MUSIC_INFO);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_PLAY_NEXT);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_PLAY_PRE);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_PLAY_MODE_CIRCLE);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_PLAY_MODE_RANDOM);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_SONG_TO_NEXT_PLAY);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_SONG_QUERY_CUR_MODE);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_CIRCLE_PLAY_PLAY_LIST);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_GET_PLAY_QUEUE);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_RANDOM_PLAY_PLAY_LIST);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_REMOVE_SONG_FROM_QUEUE);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_PLAY_MODE_QUEUE);
            filter.addAction(MusicServiceInstruction.SERVER_RECEIVER_ADD_MUSIC_TO_QUEUE);
            return filter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case MusicServiceInstruction.SERVER_RECEIVER_LOAD_MUSIC_INFO:
                    mService.loadMusicInfo((Song) intent.getSerializableExtra(MusicServiceInstruction.SERVER_PARAM_PLAY_SONG),
                            intent.getBooleanExtra(MusicServiceInstruction.SERVER_PARAM_PLAY_SONG_AUTO_PLAY, false));
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_PLAY_MUSIC:
                    mService.playMusic();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_PAUSE_MUSIC:
                    mService.pausePlay();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_SEEK_TO:
                    mService.seekTo(intent);
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_SAVE_LAST_PLAY_MUSIC:
                    mService.saveLastPlayMusic();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_CURRENT_PLAY_MUSIC:
                    mService.informCurrentPlayMusic();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_CHANGE_MUSIC:
                    mService.changeMusic((Song) intent.getSerializableExtra(MusicServiceInstruction.SERVER_PARAM_CHANGE_MUSIC));
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_GET_PLAY_PROGRESS:
                    mService.informCurrentIfPlayProgress();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_UPDATE_PLAY_MUSIC_INFO:
                    mService.updateSong((Song) intent.getSerializableExtra(MusicServiceInstruction.SERVER_PARAM_UPDATE_SONG));
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_PLAY_NEXT:
                    mService.playNextMusic();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_PLAY_PRE:
                    mService.PlayPreMusic();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_PLAY_MODE_CIRCLE:
                    mService.startCircleMode();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_PLAY_MODE_RANDOM:
                    mService.startRandomMode();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_SONG_TO_NEXT_PLAY:
                    mService.songToNextPlay((Song) intent.getSerializableExtra(MusicServiceInstruction.SERVER_PARAM_SONG_TO_NEXT_PLAY));
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_SONG_QUERY_CUR_MODE:
                    mService.notifyCurrentMode();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_CIRCLE_PLAY_PLAY_LIST:
                    mService.circlePlayPlayList((PlayList) intent.getSerializableExtra(MusicServiceInstruction.SERVER_PARAM_PLAY_LIST));
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_GET_PLAY_QUEUE:
                    mService.getPlayQueue();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_RANDOM_PLAY_PLAY_LIST:
                    mService.randomPlayPlayList((PlayList) intent.getSerializableExtra(MusicServiceInstruction.SERVER_PARAM_PLAY_LIST));
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_PLAY_MODE_QUEUE:
                    mService.startQueueMode();
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_ADD_MUSIC_TO_QUEUE:
                    mService.addMusicToQueue((Song) intent.getSerializableExtra(MusicServiceInstruction.SERVER_PARAM_QUEUE_ADD_SONG));
                    break;
                case MusicServiceInstruction.SERVER_RECEIVER_REMOVE_SONG_FROM_QUEUE:
                    mService.removeSongFromQueue((Song) intent.getSerializableExtra(MusicServiceInstruction.SERVER_PARAM_REMOVE_SONG));
                    break;
            }
        }
    }


}
