package com.susion.boring.music.presenter;

import com.susion.boring.base.ModelTranslateContract;
import com.susion.boring.base.MusicModelTranslatePresenter;
import com.susion.boring.music.model.PlayList;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.MusicServiceContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/2/24.
 */
public class MusicPlayQueueControlPresenter implements MusicServiceContract.PlayQueueControlPresenter{

    private ModelTranslateContract.MusicModeTranslate musicModeTranslate;
    private List<Song> mQueue;
    private int mPlayMode;
    private int mCurrentIndex = 0;
    private final Random mRandom;

    public MusicPlayQueueControlPresenter(List<Song> initQueue) {

        if (mQueue == null) {
            mQueue = new ArrayList<>();
        }

        for (Song song : initQueue) {
            mQueue.add(song);
        }
        mRandom = new Random();
        mCurrentIndex = 0;
        mPlayMode = QUEUE_MODE;
        musicModeTranslate = new MusicModelTranslatePresenter();
    }

    @Override
    public boolean addToPlayQueue(Song song) {
        return mQueue.add(song);
    }

    @Override
    public boolean randomPlayQueueMusic() {
        mPlayMode = RANDOM_MODE;
        return false;
    }

    @Override
    public boolean addToNextPlay(Song song) {
        mPlayMode = QUEUE_MODE;

        if (mCurrentIndex + 1 < mQueue.size()) {
            mQueue.add(mCurrentIndex + 1, song);
        } else {
            mQueue.add(0, song);
        }

        return true;
    }

    @Override
    public Song getNextPlayMusic() {
        if (mQueue.isEmpty()) {
          return null;
        }

        if (mPlayMode == CIRCLE_MODE) {

        }

        if (mPlayMode == QUEUE_MODE || mPlayMode == PLAY_LIST_CIRCLE_MODE) {
            if (mCurrentIndex < mQueue.size() - 1) {
                ++mCurrentIndex;
            } else {
                mCurrentIndex = 0;
            }
        }

        if (mPlayMode == RANDOM_MODE) {
            mCurrentIndex = mRandom.nextInt(mQueue.size());
        }

        return mQueue.get(mCurrentIndex);
    }

    @Override
    public Song getPrePlayMusic() {
        if (mQueue.isEmpty()) {
            return null;
        }

        if (mPlayMode == QUEUE_MODE || mPlayMode == PLAY_LIST_CIRCLE_MODE) {
            if (mCurrentIndex > 0){
                mCurrentIndex--;
            } else {
                mCurrentIndex = mQueue.size() - 1;
            }
        }

        if (mPlayMode == RANDOM_MODE) {
            mCurrentIndex = mRandom.nextInt(mQueue.size());
        }

        return mQueue.get(mCurrentIndex);
    }

    @Override
    public void clearPlayQueue() {
        mQueue.clear();
    }

    @Override
    public void setPlayMode(int mode) {
        if (mode == CIRCLE_MODE && mPlayMode == CIRCLE_MODE) {
            mPlayMode = QUEUE_MODE;
            return;
        }

        if (mode == RANDOM_MODE && mPlayMode == RANDOM_MODE) {
            mPlayMode = QUEUE_MODE;
            return;
        }

        this.mPlayMode = mode;
    }


    public int getPlayMode() {
        return mPlayMode;
    }

    @Override
    public Observable<Boolean> reLoadPlayQueue(final PlayList playList) {
        mQueue.clear();
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final  Subscriber<? super Boolean> subscriber) {
                musicModeTranslate.getSongFromPlayList(playList)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Song>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onNext(false);
                            }

                            @Override
                            public void onNext(List<Song> songs) {
                                mQueue.addAll(songs);
                                subscriber.onNext(true);
                            }
                        });
            }
        });

    }

    @Override
    public List<Song> getPlayQueue() {
        return mQueue;
    }

    @Override
    public boolean removeSong(Song song) {

        int songIndex = -1;

        for (int i=0; i<mQueue.size(); i++) {
            if (song.id.equals(mQueue.get(i).id)) {
                songIndex = i;
            }
        }

        if (songIndex > 0) {
            mQueue.remove(songIndex);
            return true;
        }

        return false;
    }

    @Override
    public void markCurrentPlayMusic(Song markSong) {
        if (mQueue == null || mQueue.isEmpty()) {
            return;
        }
        boolean haveSong = false;
        for (Song song : mQueue) {
            song.isPlaying = false;
            if (song.id.equals(markSong.id)) {
                song.isPlaying = true;
                haveSong = true;
            }
        }

        if (!haveSong) {
            markSong.isPlaying = true;
            mQueue.add(0, markSong);
        }
    }
}
