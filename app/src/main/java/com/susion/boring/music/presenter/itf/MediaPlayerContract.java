package com.susion.boring.music.presenter.itf;

import android.content.Context;

import com.susion.boring.base.BasePresenter;
import com.susion.boring.base.IView;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.music.model.PlayList;
import com.susion.boring.music.model.Song;

import java.util.List;

/**
 * Created by susion on 17/2/20.
 */
public interface MediaPlayerContract {

    //for panel view
    interface BaseView extends IView {
        void tryToChangeMusicByCurrentCondition(boolean playStatus, boolean needLoadMusic);

        void refreshSong(Song song);
    }

    //media player notify view
    interface LittlePlayView {
        void updateBufferedProgress(int percent);

        void updatePlayProgress(int curPos, int duration);

        void preparedPlay(int duration);

        void completionPlay();
    }

    //play music activity
    interface PlayView extends BaseView, LittlePlayView {
        void setPlayDuration(int duration);

        void refreshPlayMode(int playMode);

        void updatePlayProgressForSetMax(int curPos, int duration);

        void loadNewMusic();
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
    interface PlayMusicControlPresenter extends Presenter {
        void saveLastPlayMusic(Song song, Context c);
    }


    //client broadcast receiver
    interface ClientReceiverPresenter {
        void registerReceiver();

        void releaseResource();

        void setBaseView(BaseView view);

        void setLittlePlayView(LittlePlayView view);

        void setPlayView(PlayView view);
    }


    //Client send command interact with play service
    interface ClientCommand {
        void likeMusic(Song mSong);

        void updatePlayMusic(Song song);

        void musicToNextPlay(Song mSong);
    }

    interface ClientPlayControlCommand extends ClientCommand {
        void queryServiceIsPlaying();

        void tryToChangePlayingMusic(Song song);

        void changeToNextMusic();

        void changeToPreMusic();

        void loadMusicInfoToService(Song song, boolean autoPlay);

        void getCurrentPlayMusic();

        void pausePlay();
    }


    interface ClientPlayModeCommand extends ClientCommand {
        void queryCurrentPlayMode();

        void startCirclePlayMode();

        void startRandomPlayMode();

        void circlePlayPlayList(PlayList mData);
    }


}
