package com.susion.boring.music.view;

import android.media.MediaPlayer;

/**
 * Created by susion on 17/1/23.
 */
public interface IMediaPlayView {
    void initMusicInfoUI();


    void startMusicPlayAnimation();
    void stopMusicPlayAnimation();
    void changPlayModule();



    //new
    void initMediaProgress(String duration);
    void updateBufferedProgress(int percent);
    void updatePlayProgress(int curPos, int duration);
    void preparedPlay(MediaPlayer mPlayer);
    void completionPlay();

}
