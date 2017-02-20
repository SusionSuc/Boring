package com.susion.boring.music.presenter.itf;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.susion.boring.base.BasePresenter;
import com.susion.boring.base.BaseView;

/**
 * Created by susion on 17/2/20.
 */
public interface MediaPlayerContract {

    interface View extends BaseView<Presenter> {
        void updateBufferedProgress(int percent);
        void updatePlayProgress(int curPos, int duration);
        void preparedPlay(int duration);
        void completionPlay();

    }

    interface Presenter extends BasePresenter {
        void initMediaPlayer(String mediaUri) throws Exception;
        boolean startPlay();
        void pausePlay();
        void stopPlay();
        void releaseResource();
        void seekTo(int pos);

        boolean isPrepared();

        int getDuration();

        boolean isPlaying();

        int getCurrentProgress();

    }
}
