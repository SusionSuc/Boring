package com.susion.boring.music.presenter.itf;

import android.content.Context;

import com.susion.boring.base.BasePresenter;
import com.susion.boring.base.IView;
import com.susion.boring.music.model.Song;

/**
 * Created by susion on 17/2/20.
 */
public interface MediaPlayerContract {

       //for panel view
    interface CommunicateBaseView extends IView{
        void tryToChangeMusicByCurrentCondition(boolean playStatus, boolean needLoadMusic);
        void refreshSong(Song song);
    }

    //media player notify view
    interface BaseView{
        void updateBufferedProgress(int percent);

        void updatePlayProgress(int curPos, int duration);

        void preparedPlay(int duration);

        void completionPlay();
    }

    //play music activity
    interface PlayControlView extends CommunicateBaseView, BaseView{
        void setPlayDuration(int duration);

        void updatePlayProgressForSetMax(int curPos, int duration);

        void refreshPlayMode(int playmode);
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
        void saveLastPlayMusic(Song song, Context c);
    }


    //for communicate with music play service
    interface ClientReceiverPresenter{
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

        void loadMusicInfoToService(Song song, boolean autoPlay);

        void getCurrentPlayMusic();

        void pausePlay();


    }


}
