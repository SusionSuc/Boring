package com.susion.boring.music.presenter.itf;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.susion.boring.base.BasePresenter;
import com.susion.boring.base.BaseView;
import com.susion.boring.music.model.Song;

import java.io.Serializable;

/**
 * Created by susion on 17/2/20.
 */
public interface MediaPlayerContract {

    interface BaseView{

        Context getContext();

        void updateBufferedProgress(int percent);

        void updatePlayProgress(int curPos, int duration);

        void preparedPlay(int duration);

        void completionPlay();
    }


    interface CommunicateView extends BaseView{

        void setPlayDuration(int duration);

        void updatePlayProgressForSetMax(int curPos, int duration);

        void tryToChangeMusicByCurrentCondition(boolean playStatus);

        void refreshSong(Song song);

        void refreshPlayMode(int serializableExtra);
    }

    //media player
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

    //for music play
    interface PlayMusicControlPresenter extends Presenter{

        void randomPlayMusic();

        void circlePlayMusic();

        void saveLastPlayMusic(Song song, Context c);

        void nextPlay(Song song);
    }


    //for communicate with music play service
    interface PlayMusicCommunicatePresenter{

        void queryServiceIsPlaying();

        void tryToChangePlayingMusic(Song song);

        void releaseResource();

        void updatePlayMusic(Song song);

        void likeMusic(Song mSong);

        void changeToNextMusic();

        void changeToPreMusic();

        void startCirclePlayMode();

        void startRandomPlayMode();

        void musicToNextPlay(Song mSong);
    }



}
