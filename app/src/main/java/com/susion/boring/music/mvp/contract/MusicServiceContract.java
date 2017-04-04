package com.susion.boring.music.mvp.contract;

import android.content.Context;
import android.content.Intent;

import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.model.Song;

import java.util.List;

import rx.Observable;

/**
 * Created by susion on 17/2/24.
 */
public interface MusicServiceContract {

    interface Service {
        void loadMusicInfo(Song song, boolean autoPlay);

        void playMusic();

        void pausePlay();

        void seekTo(Intent intent);

        void saveLastPlayMusic();

        void informCurrentPlayMusic();

        void informCurrentIfPlayProgress();

        void updateSong(Song song);

        Context getContext();

        void notifyCurrentPlayMusic(boolean isPlaying);

        void clear();

        void playNextMusic();

        void PlayPreMusic();

        void changeMusic(Song song);

        void startCircleMode();

        void startRandomMode();

        void songToNextPlay(Song serializableExtra);

        void notifyCurrentMode();

        void circlePlayPlayList(PlayList playList);

        void getPlayQueue();

        void randomPlayPlayList(PlayList playList);

        void removeSongFromQueue(Song serializableExtra);

        void startQueueMode();

        void addMusicToQueue(Song song);
    }


    interface PlayQueueControlPresenter {
        int QUEUE_MODE = 0;
        int RANDOM_MODE = 1;
        int CIRCLE_MODE = 2;
        int PLAY_LIST_CIRCLE_MODE = 3;

        boolean addToPlayQueue(Song song);

        boolean randomPlayQueueMusic();

        boolean addToNextPlay(Song song);

        Song getNextPlayMusic();

        Song getPrePlayMusic();

        void clearPlayQueue();

        void setPlayMode(int mode);

        int getPlayMode();

        Observable<Boolean> reLoadPlayQueue(PlayList playList);

        List<Song> getPlayQueue();

        boolean removeSong(Song song);

        void markCurrentPlayMusic(Song song);

    }

}
