package com.susion.boring.music.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.db.DbManager;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.utils.BroadcastUtils;
import com.susion.boring.utils.ToastUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/2/22.
 */
public class PlayMusicCommunicatePresenter implements MediaPlayerContract.PlayMusicCommunicatePresenter {

    private ClientMusicReceiver mReceiver;
    private MediaPlayerContract.CommunicateView mView;

    public PlayMusicCommunicatePresenter(MediaPlayerContract.CommunicateView view) {
        mReceiver = new ClientMusicReceiver();
        mView = view;
    }

    @Override
    public void queryServiceIsPlaying() {
        LocalBroadcastManager.getInstance(mView.getContext()).registerReceiver(mReceiver, mReceiver.getIntentFilter());
        BroadcastUtils.sendIntentAction(mView.getContext(), MusicInstruction.SERVICE_RECEIVER_QUERY_IS_PLAYING);
    }

    @Override
    public void tryToChangePlayingMusic(Song song) {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_CHANGE_MUSIC);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_CHANGE_MUSIC, song);
        LocalBroadcastManager.getInstance(mView.getContext()).sendBroadcast(intent);
    }

    @Override
    public void releaseResource() {
        LocalBroadcastManager.getInstance(mView.getContext()).unregisterReceiver(mReceiver);
    }

    @Override
    public void updatePlayMusic(Song song) {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_UPDATE_PLAY_MUSIC_INFO);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_UPDATE_SONG, song);
        LocalBroadcastManager.getInstance(mView.getContext()).sendBroadcast(intent);
    }

    @Override
    public void likeMusic(Song mSong) {
        updatePlayMusic(mSong);
        new DbBaseOperate<SimpleSong>(DbManager.getLiteOrm(), mView.getContext(), SimpleSong.class)
                .update(mSong.translateToSimpleSong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("喜欢失败");
                    }

                    @Override
                    public void onNext(Boolean b) {
                        if (b) {
                            ToastUtils.showShort("喜欢成功");
                        } else {
                            ToastUtils.showShort("喜欢失败");
                        }
                    }
                });
    }


    public class ClientMusicReceiver extends BroadcastReceiver {
        public IntentFilter getIntentFilter(){
            IntentFilter filter = new IntentFilter();
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_PLAYER_PREPARED);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_UPDATE_BUFFERED_PROGRESS);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_SET_DURATION);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_CURRENT_IS_PALING);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_PROGRESS);
            return filter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case MusicInstruction.CLIENT_RECEIVER_PLAYER_PREPARED:
                    mView.preparedPlay(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PREPARED_TOTAL_DURATION, 0));
                    break;
                case MusicInstruction.CLIENT_RECEIVER_UPDATE_BUFFERED_PROGRESS:
                    mView.updateBufferedProgress(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_BUFFERED_PROGRESS, 0));
                    break;
                case MusicInstruction.CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS:
                    mView.updatePlayProgress(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_CUR_POS, 0),
                            intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_DURATION, 0));
                    break;
                case MusicInstruction.CLIENT_RECEIVER_SET_DURATION:
                    mView.setPlayDuration(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_MEDIA_DURATION, 0));
                    break;
                case MusicInstruction.CLIENT_RECEIVER_CURRENT_IS_PALING:
                    boolean playStatus = intent.getBooleanExtra(MusicInstruction.CLIENT_PARAM_IS_PLAYING, false);
                    mView.tryToChangeMusicByCurrentCondition(playStatus);
                    BroadcastUtils.sendIntentAction(mView.getContext(), MusicInstruction.SERVICE_RECEIVER_GET_PLAY_PROGRESS);
                    break;
                case MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_PROGRESS:
                    final int curPos = intent.getIntExtra(MusicInstruction.CLIENT_PARAM_CURRENT_PLAY_PROGRESS, 0);
                    final int left = intent.getIntExtra(MusicInstruction.CLIENT_PARAM_MEDIA_DURATION, 0);
                    mView.updatePlayProgressForSetMax(curPos, left);
                    break;
            }
        }
    }
}
