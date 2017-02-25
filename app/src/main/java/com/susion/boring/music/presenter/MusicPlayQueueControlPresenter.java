package com.susion.boring.music.presenter;

import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.MusicServiceContract;

import java.util.List;
import java.util.Random;

/**
 * Created by susion on 17/2/24.
 */
public class MusicPlayQueueControlPresenter implements MusicServiceContract.PlayQueueControlPresenter {

    private List<Song> mQueue;
    private int mPlayMode;
    private int mCurrentIndex = 0;

    private final Random mRandom;

    public MusicPlayQueueControlPresenter(List<Song> initQueue) {
        mQueue = initQueue;
        mRandom = new Random();
        mCurrentIndex = 0;
        mPlayMode = QUEUE_MODE;
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

        if (mPlayMode == QUEUE_MODE) {
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

        if (mPlayMode == QUEUE_MODE) {
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
}
