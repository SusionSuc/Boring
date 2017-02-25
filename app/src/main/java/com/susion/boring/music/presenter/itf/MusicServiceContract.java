package com.susion.boring.music.presenter.itf;

import android.content.Context;
import android.content.Intent;

import com.susion.boring.music.model.Song;

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

        void tryToChangeMusic(Song song);

        void informCurrentIfPlaying();

        void informCurrentIfPlayProgress();

        void updateSong(Song song);

        Context getContext();

        void notifyMediaDuration();

        void notifyCurrentPlayMusic();

        void clear();

        void playNextMusic();

        void PlayPreMusic();

        void changeMusic();

        void notifyRefreshSong();

        void startCircleMode();

        void startRandomMode();

        void songToNextPlay(Song serializableExtra);

        void notifyCurrentMode();
    }

    interface ReceiverPresenter {
        void releaseResource();
    }

    interface PlayQueueControlPresenter {
          int QUEUE_MODE = 0;
          int RANDOM_MODE = 1;
          int CIRCLE_MODE = 2;

        boolean addToPlayQueue(Song song);

        boolean randomPlayQueueMusic();

        boolean addToNextPlay(Song song);

        Song getNextPlayMusic();

        Song getPrePlayMusic();

        void clearPlayQueue();

        void setPlayMode(int mode);

        int getPlayMode();
    }

}
