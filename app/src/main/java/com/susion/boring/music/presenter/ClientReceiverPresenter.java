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

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/2/22.
 */
public class ClientReceiverPresenter implements MediaPlayerContract.ClientReceiverPresenter{

    protected ClientMusicReceiver mReceiver;
    protected MediaPlayerContract.BaseView mBaseView;
    protected MediaPlayerContract.LittlePlayView mLittlePlayView;
    protected MediaPlayerContract.PlayView mPlayView;

    private Context mContext;

    public ClientReceiverPresenter(Context mContext) {
        this.mContext = mContext;
        mReceiver = new ClientMusicReceiver();
        registerReceiver();
    }

    public void setBaseView(MediaPlayerContract.BaseView mBaseView) {
        this.mBaseView = mBaseView;
    }

    @Override
    public void setLittlePlayView(MediaPlayerContract.LittlePlayView view) {
        this.mLittlePlayView = view;
    }

    @Override
    public void setPlayView(MediaPlayerContract.PlayView view) {
        this.mPlayView = view;
    }

    @Override
    public void registerReceiver() {
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver, mReceiver.getIntentFilter());
    }

    @Override
    public void releaseResource() {
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mReceiver);
    }


    public class ClientMusicReceiver extends BroadcastReceiver {
        public IntentFilter getIntentFilter(){
            IntentFilter filter = new IntentFilter();
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_PLAYER_PREPARED);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_UPDATE_BUFFERED_PROGRESS);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_SET_DURATION);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_PROGRESS);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_REFRESH_MUSIC);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_REFRESH_MODE);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_CURRENT_IS_PALING);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_MUSIC);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_PLAY_QUEUE);
            return filter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case MusicInstruction.CLIENT_RECEIVER_PLAYER_PREPARED:
                    if (mLittlePlayView != null) {
                        mLittlePlayView.preparedPlay(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PREPARED_TOTAL_DURATION, 0));
                    }

                    if (mPlayView != null) {
                        mPlayView.preparedPlay(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PREPARED_TOTAL_DURATION, 0));
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_UPDATE_BUFFERED_PROGRESS:
                    if (mLittlePlayView != null) {
                        mLittlePlayView.updateBufferedProgress(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_BUFFERED_PROGRESS, 0));
                    }

                    if (mPlayView != null) {
                        mPlayView.updateBufferedProgress(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_BUFFERED_PROGRESS, 0));
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS:
                    if (mLittlePlayView != null) {
                        mLittlePlayView.updatePlayProgress(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_CUR_POS, 0),
                                intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_DURATION, 0));
                    }

                    if (mPlayView != null) {
                        mPlayView.updatePlayProgress(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_CUR_POS, 0),
                                intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_DURATION, 0));
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_SET_DURATION:
                    if (mPlayView != null) {
                        mPlayView.setPlayDuration(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_MEDIA_DURATION, 0));
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_CURRENT_IS_PALING:
                    boolean playStatus = intent.getBooleanExtra(MusicInstruction.CLIENT_PARAM_IS_PLAYING, false);
                    boolean needLoadMusic = intent.getBooleanExtra(MusicInstruction.CLIENT_PARAM_NEED_LOAD_MUSIC, false);
                    BroadcastUtils.sendIntentAction(mContext, MusicInstruction.SERVICE_RECEIVER_GET_PLAY_PROGRESS);

                    if (mBaseView != null) {
                        mBaseView.tryToChangeMusicByCurrentCondition(playStatus, needLoadMusic);
                    }

                    if (mPlayView != null) {
                        mPlayView.tryToChangeMusicByCurrentCondition(playStatus, needLoadMusic);
                    }

                    break;
                case MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_PROGRESS:
                    final int curPos = intent.getIntExtra(MusicInstruction.CLIENT_PARAM_CURRENT_PLAY_PROGRESS, 0);
                    final int left = intent.getIntExtra(MusicInstruction.CLIENT_PARAM_MEDIA_DURATION, 0);
                    if (mPlayView != null) {
                        mPlayView.updatePlayProgressForSetMax(curPos, left);
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_REFRESH_MUSIC:
                    if (mBaseView != null) {
                        mBaseView.refreshSong((Song) intent.getSerializableExtra(MusicInstruction.CLIENT_PARAM_REFRESH_SONG));
                    }
                    if (mPlayView != null) {
                        mPlayView.refreshSong((Song) intent.getSerializableExtra(MusicInstruction.CLIENT_PARAM_REFRESH_SONG));
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_REFRESH_MODE:
                    if (mPlayView != null) {
                        mPlayView.refreshPlayMode((int) intent.getSerializableExtra(MusicInstruction.CLIENT_PARAM_PLAY_MODE));
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_MUSIC:
                    Song song = (Song) intent.getSerializableExtra(MusicInstruction.CLIENT_PARAM_CURRENT_PLAY_MUSIC);
                    if (song != null) {
                        if (mBaseView != null) {
                            mBaseView.refreshSong(song);
                        }

                        if (mPlayView != null) {
                            mPlayView.refreshSong(song);
                        }
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_PLAY_QUEUE:
                    List<Song> songs = (List<Song>) intent.getSerializableExtra(MusicInstruction.CLIENT_PARAM_PLAY_QUEUE);
                    if (mPlayView != null && songs != null) {
                        mPlayView.setPlayQueue(songs);

                    }
                    break;
            }
        }
    }
}
