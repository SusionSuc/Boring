package com.susion.boring.music.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.http.APIHelper;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.music.mvp.model.PlayListSong;
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

/**
 * Created by susion on 17/2/13.
 */
public class MusicPlayerService implements MediaPlayerContract.MediaPlayerRefreshView, MusicServiceContract.Service, BaseServiceContract {

    private static final String PLAY_NOT_IS_PLAY_LIST = "PLAY_NOT_IS_PLAY_LIST";
    private MediaPlayerContract.PlayMusicControlPresenter mMusicPlayControlPresenter;
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
        mMusicPlayControlPresenter = new PlayMusicPresenter(this, mServiceParent);
        mReceiverPresenter = new ServiceReceiverPresenter(this);

        APIHelper.subscribeSimpleRequest(mDbOperator.getInitPlayQueue(), new CommonObserver<List<Song>>() {
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
                mMusicPlayControlPresenter.initMediaPlayer(mSong.audio);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeMusic(Song song) {
        if (song == null) {
            return;
        }

        mSong = song;
        if (mSong.fromPlayList) {
            APIHelper.subscribeSimpleRequest(new MusicModelTranslatePresenter().checkIfHasPlayUrl(mSong), new CommonObserver<Boolean>() {
                @Override
                public void onNext(Boolean flag) {
                    if (flag) {
                        preparePlay();
                    }
                }
            });
        } else {
            if (!mSong.hasDown) {
                APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getSongDetail(Integer.valueOf(mSong.id)), new CommonObserver<PlayListSong>() {
                    @Override
                    public void onNext(PlayListSong songs) {
                        if (songs != null && !songs.getData().isEmpty()) {
                            mSong.audio = songs.getData().get(0).getUrl();
                            preparePlay();
                        }
                    }
                });
            }
            preparePlay();
        }

    }

    private void preparePlay() {
        mAutoPlay = true;
        try {
            mMusicPlayControlPresenter.initMediaPlayer(mSong.audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyCurrentPlayMusic(true);
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
        if (!mQueueIsPrepare) {
            return;
        }

        if (mPlayQueuePresenter.getPlayQueue().isEmpty()) {
            changeMusic(song);
        }

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
            mMusicPlayControlPresenter.stopPlay();
            mQueueIsPrepare = false;
            APIHelper.subscribeSimpleRequest(mPlayQueuePresenter.reLoadPlayQueue(playList), new CommonObserver<Boolean>() {
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
            mMusicPlayControlPresenter.stopPlay();
            mQueueIsPrepare = false;
            APIHelper.subscribeSimpleRequest(mPlayQueuePresenter.reLoadPlayQueue(playList), new CommonObserver<Boolean>() {
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
    public void updatePlayProgress(int curPos, int duration, int max) {
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_PLAY_PROGRESS_CUR_POS, curPos);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_PLAY_PROGRESS_DURATION, duration);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_PLAY_PROGRESS_MAX_DURATION, mMusicPlayControlPresenter.getDuration());
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void preparedPlay(int duration) {
        mPlayQueuePresenter.markCurrentPlayMusic(mSong);
        if (mAutoPlay) {
            mMusicPlayControlPresenter.startPlay();
        }
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_PLAYER_PREPARED);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_PREPARED_TOTAL_DURATION, duration);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void informCurrentPlayMusic() {
        if (mSong != null) {
            notifyCurrentPlayMusic(mMusicPlayControlPresenter.isPlaying());
            return;
        }
        String songId = SPUtils.getStringFromConfig(SPUtils.KEY_LAST_PLAY_MUSIC);
        APIHelper.subscribeSimpleRequest(mDbOperator.query(songId), new CommonObserver<SimpleSong>() {
            @Override
            public void onNext(SimpleSong s) {
                if (s != null) {
                    mSong = s.translateToSong();
                    notifyCurrentPlayMusic(mMusicPlayControlPresenter.isPlaying());
                }
            }
        });
    }

    @Override
    public void notifyCurrentPlayMusic(boolean isPlaying) {
        Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_CURRENT_PLAY_MUSIC);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_CURRENT_PLAY_MUSIC, mSong);
        intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_CURRENT_PLAY_MUSIC_PLAY_STATUS, isPlaying);
        LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
    }

    @Override
    public void informCurrentIfPlayProgress() {
        if (mMusicPlayControlPresenter.isPrepared()) {
            Intent intent = new Intent(MusicServiceInstruction.CLIENT_RECEIVER_CURRENT_PLAY_PROGRESS);
            intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_CURRENT_PLAY_PROGRESS, mMusicPlayControlPresenter.getCurrentProgress());
            intent.putExtra(MusicServiceInstruction.CLIENT_PARAM_MEDIA_DURATION, mMusicPlayControlPresenter.getDuration());
            LocalBroadcastManager.getInstance(mServiceParent).sendBroadcast(intent);
        }
    }

    @Override
    public void saveLastPlayMusic() {
        mMusicPlayControlPresenter.stopPlay();
        mMusicPlayControlPresenter.saveLastPlayMusic(mSong, mServiceParent);
    }

    @Override
    public void completionPlay() {
        if (!playQueueIsPrepare()) return;
        mSong = mPlayQueuePresenter.getNextPlayMusic();
        changeMusic(mSong);
    }

    @Override
    public void pausePlay() {
        mMusicPlayControlPresenter.pausePlay();
    }

    @Override
    public void updateSong(Song song) {
        mSong = song;
    }

    @Override
    public void seekTo(Intent intent) {
        mMusicPlayControlPresenter.seekTo(intent.getIntExtra(MusicServiceInstruction.SERVER_PARAM_SEEK_TO_POS, 0));
        if (!mMusicPlayControlPresenter.isPlaying() && mMusicPlayControlPresenter.isPrepared()) {
            mMusicPlayControlPresenter.startPlay();
        }
    }

    @Override
    public void playMusic() {
        if (!mMusicPlayControlPresenter.isPrepared()) {
            mAutoPlay = true;
            return;
        }
        mMusicPlayControlPresenter.startPlay();
    }

    @Override
    public void clear() {
        saveLastPlayMusic();
        mMusicPlayControlPresenter.releaseResource();
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
        changeMusic(song);
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
