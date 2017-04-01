package com.susion.boring.music.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.music.mvp.presenter.MusicModelTranslatePresenter;
import com.susion.boring.base.service.BaseServiceContract;
import com.susion.boring.db.DbManager;
import com.susion.boring.music.mvp.model.SimpleSong;
import com.susion.boring.db.operate.MusicDbOperator;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.music.mvp.presenter.MusicPlayQueueControlPresenter;
import com.susion.boring.music.mvp.presenter.PlayMusicPresenter;
import com.susion.boring.music.mvp.presenter.ServiceReceiverPresenter;
import com.susion.boring.music.mvp.contract.MediaPlayerContract;
import com.susion.boring.music.mvp.contract.MusicServiceContract;
import com.susion.boring.utils.SPUtils;
import com.susion.boring.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/2/13.
 */
public class MusicPlayerService implements MediaPlayerContract.LittlePlayView, MusicServiceContract.Service, BaseServiceContract {

    private static final String PLAY_NOT_IS_PLAY_LIST = "PLAY_NOT_IS_PLAY_LIST";

    private MediaPlayerContract.PlayMusicControlPresenter mMusicPlayContrlPresenter;
    private MusicDbOperator mDbOperator;
    private BaseServiceContract.ReceiverPresenter mReceiverPresenter;
    private MusicServiceContract.PlayQueueControlPresenter mPlayQueuePresenter;
    private boolean mQueueIsPrepare;

    private Song mSong;    //current play music
    private String mHasLoadPlayListId = PLAY_NOT_IS_PLAY_LIST;
    private boolean mAutoPlay;

    private Service mServiceParent;

    public MusicPlayerService(Service mServiceParent) {
        this.mServiceParent = mServiceParent;
    }

    @Override
    public void initService() {
        mQueueIsPrepare = false;
        mDbOperator = new MusicDbOperator(DbManager.getLiteOrm(), getContext(), SimpleSong.class);
        mMusicPlayContrlPresenter = new PlayMusicPresenter(this, mServiceParent);
        mReceiverPresenter = new ServiceReceiverPresenter(this);

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


    @Override
    public void loadMusicInfo(Song song, boolean autoPlay) {
        mAutoPlay = autoPlay;
        if (song != null) {
            mSong = song;
            try {
                mMusicPlayContrlPresenter.initMediaPlayer(mSong.audio);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void queryNeedToChangeMusic(Song song) {
        boolean canChange;
        if (mSong != null && mMusicPlayContrlPresenter.isPrepared()) {
            if (mSong.id.equals(song.id)) {
                canChange = false;
            } else {
                canChange = true;
            }
        } else {
            canChange = true;
        }

        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_CAN_CHANGE_MUSIC);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_CAN_CHANGE_MUSIC, canChange);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void tryToChangeMusic(Song song) {
        mSong = song;
        preparePlay();
    }

    @Override
    public void changeMusic() {
        if (mSong == null) {
            return;
        }

        if (mSong.fromPlayList) {
            new MusicModelTranslatePresenter().checkIfHasPlayUrl(mSong)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Boolean flag) {
                            if (flag) {
                                preparePlay();
                            }
                        }
                    });
        } else {
            preparePlay();
        }

    }

    private void preparePlay() {
        mAutoPlay = true;

        try {
            mMusicPlayContrlPresenter.initMediaPlayer(mSong.audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyRefreshSong();
    }

    @Override
    public void notifyRefreshSong() {
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_REFRESH_MUSIC);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_REFRESH_SONG, mSong);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
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
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_REFRESH_MODE);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_PLAY_MODE, mode);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void circlePlayPlayList(final PlayList playList) {
        if (mPlayQueuePresenter != null && !playList.getId().equals(mHasLoadPlayListId)) {
            mMusicPlayContrlPresenter.stopPlay();
            mQueueIsPrepare = false;
            mPlayQueuePresenter.reLoadPlayQueue(playList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showShort("装载音乐列表失败");
                        }

                        @Override
                        public void onNext(Boolean flag) {
                            if (flag) {
                                mHasLoadPlayListId = playList.getId();
                                mQueueIsPrepare = true;
                                mPlayQueuePresenter.setPlayMode(MusicServiceContract.PlayQueueControlPresenter.PLAY_LIST_CIRCLE_MODE);
                                playNextMusic();
                            }
                        }
                    });
        }
    }

    @Override
    public void randomPlayPlayList(final PlayList playList) {
        if (mPlayQueuePresenter != null && !playList.getId().equals(mHasLoadPlayListId)) {
            mMusicPlayContrlPresenter.stopPlay();
            mQueueIsPrepare = false;
            mPlayQueuePresenter.reLoadPlayQueue(playList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showShort("装载音乐列表失败");
                        }

                        @Override
                        public void onNext(Boolean flag) {
                            if (flag) {
                                mHasLoadPlayListId = playList.getId();
                                mQueueIsPrepare = true;
                                mPlayQueuePresenter.setPlayMode(MusicServiceContract.PlayQueueControlPresenter.RANDOM_MODE);
                                playNextMusic();
                            }
                        }
                    });
        }
    }

    @Override
    public void removeSongFromQueue(Song song) {
        mPlayQueuePresenter.removeSong(song);
    }

    @Override
    public void startQueueMode() {
        mPlayQueuePresenter.setPlayMode(MusicServiceContract.PlayQueueControlPresenter.QUEUE_MODE);
    }

    @Override
    public void addMusicToQueue(Song song) {
        mPlayQueuePresenter.addToPlayQueue(song);
    }


    @Override
    public void getPlayQueue() {
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_PLAY_QUEUE);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_PLAY_QUEUE, (Serializable) mPlayQueuePresenter.getPlayQueue());
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void notifyMediaDuration() {
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_SET_DURATION);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_MEDIA_DURATION, mMusicPlayContrlPresenter.getDuration());
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public Context getContext() {
        return mServiceParent;
    }

    @Override
    public void updateBufferedProgress(int percent) {
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_UPDATE_BUFFERED_PROGRESS);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_BUFFERED_PROGRESS, percent);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void updatePlayProgress(int curPos, int duration) {
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_PLAY_PROGRESS_CUR_POS, curPos);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_PLAY_PROGRESS_DURATION, duration);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void preparedPlay(int duration) {
        mPlayQueuePresenter.markCurrentPlayMusic(mSong);
        if (mAutoPlay) {
            mMusicPlayContrlPresenter.startPlay();
        }
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_PLAYER_PREPARED);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_PREPARED_TOTAL_DURATION, duration);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void informCurrentPlayMusic() {
        if (mSong != null) {
            notifyCurrentPlayMusic();
            return;
        }

        String songId = SPUtils.getStringFromConfig(SPUtils.KEY_LAST_PLAY_MUSIC);
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
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_CURRENT_PLAY_MUSIC);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_CURRENT_PLAY_MUSIC, mSong);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void informCurrentIfPlaying() {
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_CURRENT_IS_PALING);
        boolean isPlaying, needLoad;
        if (mMusicPlayContrlPresenter.isPrepared()) {
            if (mMusicPlayContrlPresenter.isPlaying()) {
                isPlaying = true;
            } else {
                isPlaying = false;
            }
            needLoad = false;
        } else {
            needLoad = false;
            isPlaying = false;
        }

//        if (mSong != null) {
//            if (mSong.id.equals(song.id)) {   什么用户体验, 去死吧
//                needLoad = false;
//            }
//        }

        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_IS_PLAYING, isPlaying);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_NEED_LOAD_MUSIC, needLoad);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void informCurrentIfPlayProgress() {
        if (mMusicPlayContrlPresenter.isPrepared()) {
            Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_CURRENT_PLAY_PROGRESS);
            intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_CURRENT_PLAY_PROGRESS, mMusicPlayContrlPresenter.getCurrentProgress());
            intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_MEDIA_DURATION, mMusicPlayContrlPresenter.getDuration());
            LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
        }
    }

    @Override
    public void saveLastPlayMusic() {
        mMusicPlayContrlPresenter.stopPlay();
        mMusicPlayContrlPresenter.saveLastPlayMusic(mSong, mServiceParent);
    }

    @Override
    public void completionPlay() {
        if (!playQueueIsPrepare()) return;
        mSong = mPlayQueuePresenter.getNextPlayMusic();
        changeMusic();
    }

    @Override
    public void pausePlay() {
        mMusicPlayContrlPresenter.pausePlay();
    }

    @Override
    public void updateSong(Song song) {
        mSong = song;
    }

    @Override
    public void seekTo(Intent intent) {
        mMusicPlayContrlPresenter.seekTo(intent.getIntExtra(MusicServiceInstruction.SERVICE_PARAM_SEEK_TO_POS, 0));
        if (!mMusicPlayContrlPresenter.isPlaying() && mMusicPlayContrlPresenter.isPrepared()) {
            mMusicPlayContrlPresenter.startPlay();
        }
    }

    @Override
    public void playMusic() {
        if (!mMusicPlayContrlPresenter.isPrepared()) {
            mAutoPlay = true;
            return;
        }
        mMusicPlayContrlPresenter.startPlay();
    }

    @Override
    public void clear() {
        saveLastPlayMusic();
        mMusicPlayContrlPresenter.releaseResource();
        mReceiverPresenter.releaseResource();
    }

    @Override
    public void playNextMusic() {
        if (!playQueueIsPrepare()) return;
        changeMusicFromQueue(mPlayQueuePresenter.getNextPlayMusic());
    }

    @Override
    public void PlayPreMusic() {
        if (!playQueueIsPrepare()) return;
        changeMusicFromQueue(mPlayQueuePresenter.getPrePlayMusic());
    }

    private void changeMusicFromQueue(Song song) {
        if (song == null) {
            notifyQueueNoMoreMusic();
            return;
        }
        mSong = song;
        changeMusic();
    }

    private void notifyQueueNoMoreMusic() {
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_QUEUE_NO_MORE_MUSIC);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
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
    public void onTaskMoved() {
        clear();
    }

    //手动停止, 会被调用
    @Override
    public void onDestroy() {
        clear();
    }
}
