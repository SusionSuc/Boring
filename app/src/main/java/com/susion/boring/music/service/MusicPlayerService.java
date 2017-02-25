package com.susion.boring.music.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.susion.boring.db.DbManager;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.db.operate.MusicDbOperator;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.MusicPlayQueueControlPresenter;
import com.susion.boring.music.presenter.MusicPlayerReceiverPresenter;
import com.susion.boring.music.presenter.PlayMusicPresenter;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.music.presenter.itf.MusicServiceContract;
import com.susion.boring.utils.SPUtils;
import com.susion.boring.utils.ToastUtils;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/2/13.
 */
public class MusicPlayerService extends Service implements MediaPlayerContract.BaseView, MusicServiceContract.Service{

    private MediaPlayerContract.PlayMusicControlPresenter mPresenter;
    private MusicDbOperator mDbOperator;
    private MusicServiceContract.ReceiverPresenter mReceiverPresenter;
    private MusicServiceContract.PlayQueueControlPresenter mPlayQueuePresenter;
    private boolean mQueueIsPrepare;


    private static final String TAG = MusicPlayerService.class.getSimpleName();
    public static final String SERVICE_ACTION = "MUSIC_SERVICE";
    private Song mSong;          //current play music
    private boolean mAutoPlay;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mQueueIsPrepare = false;
        mDbOperator = new MusicDbOperator(DbManager.getLiteOrm(), getContext(), SimpleSong.class);
        mPresenter = new PlayMusicPresenter(this, this, new DbBaseOperate<SimpleSong>(DbManager.getLiteOrm(), this, SimpleSong.class));
        mReceiverPresenter = new MusicPlayerReceiverPresenter(this);

        mDbOperator.getInitPlayQueue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Song>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Song> songs) {
                        mQueueIsPrepare = true;
                        mPlayQueuePresenter = new MusicPlayQueueControlPresenter(songs);
                    }
                });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void loadMusicInfo(Song song, boolean autoPlay) {
        mAutoPlay = autoPlay;
        if (song != null) {
            mSong = song;
            try {
                mPresenter.initMediaPlayer(mSong.audio);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void tryToChangeMusic(Song song) {
        mAutoPlay = true;
        if (mSong != null) {
            if (song.id.equals(mSong.id) && mPresenter.isPrepared()) {
                notifyMediaDuration();
                return;
            }
        }

        mSong = song;
        try {
            mPresenter.initMediaPlayer(mSong.audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeMusic() {
        mAutoPlay = true;
        try {
            mPresenter.initMediaPlayer(mSong.audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyRefreshSong();
    }

    @Override
    public void notifyRefreshSong() {
        Intent intent = new Intent(MusicInstruction.CLIENT_RECEIVER_REFRESH_MUSIC);
        intent.putExtra(MusicInstruction.CLIENT_PARAM_REFRESH_SONG, mSong);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void startCircleMode() {
        mPlayQueuePresenter.setPlayMode(MusicServiceContract.PlayQueueControlPresenter.CIRCLE_MODE);
        notifyCurrentMode();
    }

    @Override
    public void startRandomMode() {
        mPlayQueuePresenter.setPlayMode(MusicServiceContract.PlayQueueControlPresenter.RANDOM_MODE);
        notifyCurrentMode();
    }

    @Override
    public void songToNextPlay(Song song) {
        mPlayQueuePresenter.addToNextPlay(song);
    }

    @Override
    public void notifyCurrentMode() {
        int mode = mPlayQueuePresenter.getPlayMode();
        Intent intent = new Intent(MusicInstruction.CLIENT_RECEIVER_REFRESH_MODE);
        intent.putExtra(MusicInstruction.CLIENT_PARAM_PLAY_MODE, mode);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void notifyMediaDuration() {
        Intent intent = new Intent(MusicInstruction.CLIENT_RECEIVER_SET_DURATION);
        intent.putExtra(MusicInstruction.CLIENT_PARAM_MEDIA_DURATION, mPresenter.getDuration());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateBufferedProgress(int percent) {
        Intent intent = new Intent(MusicInstruction.CLIENT_RECEIVER_UPDATE_BUFFERED_PROGRESS);
        intent.putExtra(MusicInstruction.CLIENT_PARAM_BUFFERED_PROGRESS, percent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void updatePlayProgress(int curPos, int duration) {
        Intent intent = new Intent(MusicInstruction.CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS);
        intent.putExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_CUR_POS, curPos);
        intent.putExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_DURATION, duration);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void preparedPlay(int duration) {
        if (mAutoPlay) {
            mPresenter.startPlay();
        }

        Intent intent = new Intent(MusicInstruction.CLIENT_RECEIVER_PLAYER_PREPARED);
        intent.putExtra(MusicInstruction.CLIENT_PARAM_PREPARED_TOTAL_DURATION, duration);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    @Override
    public void informCurrentPlayMusic() {
        if (mSong != null) {
            notifyCurrentPlayMusic();
            return;
        }

        String songId = SPUtils.getStringFromMusicConfig(SPUtils.MUSIC_CONFIG_LAST_PLAY_MUSIC, this);
        mDbOperator.query(songId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SimpleSong>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SimpleSong s) {
                if (s != null) {
                    mSong = s.translateToSong();
                    notifyCurrentPlayMusic();
                }
            }
        });
    }

    @Override
    public void notifyCurrentPlayMusic() {
        Intent intent = new Intent(MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_MUSIC);
        intent.putExtra(MusicInstruction.CLIENT_PARAM_CURRENT_PLAY_MUSIC, mSong);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void informCurrentIfPlaying() {
        Intent intent = new Intent(MusicInstruction.CLIENT_RECEIVER_CURRENT_IS_PALING);
        if (mPresenter.isPrepared()) {
            if (mPresenter.isPlaying()) {
                intent.putExtra(MusicInstruction.CLIENT_PARAM_IS_PLAYING, true);
            } else {
                intent.putExtra(MusicInstruction.CLIENT_PARAM_IS_PLAYING, false);
            }
            intent.putExtra(MusicInstruction.CLIENT_PARAM_NEED_LOAD_MUSIC, false);
        } else {
            intent.putExtra(MusicInstruction.CLIENT_PARAM_NEED_LOAD_MUSIC, true);
            intent.putExtra(MusicInstruction.CLIENT_PARAM_IS_PLAYING, false);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void informCurrentIfPlayProgress() {
        if (mPresenter.isPrepared()) {
            Intent intent = new Intent(MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_PROGRESS);
            intent.putExtra(MusicInstruction.CLIENT_PARAM_CURRENT_PLAY_PROGRESS, mPresenter.getCurrentProgress());
            intent.putExtra(MusicInstruction.CLIENT_PARAM_MEDIA_DURATION, mPresenter.getDuration());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    @Override
    public void saveLastPlayMusic() {
        mPresenter.saveLastPlayMusic(mSong, this);
    }

    @Override
    public void completionPlay() {
        if (!playQueueIsPrepare()) return;

        mSong = mPlayQueuePresenter.getNextPlayMusic();
        changeMusic();
    }

    @Override
    public void pausePlay() {
        mPresenter.pausePlay();
    }

    @Override
    public void updateSong(Song song) {
        mSong = song;
    }

    @Override
    public void seekTo(Intent intent) {
        mPresenter.seekTo(intent.getIntExtra(MusicInstruction.SERVICE_PARAM_SEEK_TO_POS, 0));
    }

    @Override
    public void playMusic() {
        if (!mPresenter.isPrepared()) {
            mAutoPlay = true;
            return;
        }
        mPresenter.startPlay();
    }

    @Override
    public void clear() {
        saveLastPlayMusic();
        mPresenter.releaseResource();
        mReceiverPresenter.releaseResource();
    }

    @Override
    public void playNextMusic() {
        if (!playQueueIsPrepare()) return;

        mSong = mPlayQueuePresenter.getNextPlayMusic();
        changeMusic();
    }

    @Override
    public void PlayPreMusic() {
        if (!playQueueIsPrepare()) return;

        mSong = mPlayQueuePresenter.getPrePlayMusic();
        changeMusic();
    }

    private boolean playQueueIsPrepare() {
        if (!mQueueIsPrepare) {
            ToastUtils.showShort("播放队列正在准备");
            return false;
        }
        return true;
    }

    //用长按home调出最近运行历史，在这里面清除软件,可能会调用
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        clear();
    }

    //手动停止, 会被调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }


}
