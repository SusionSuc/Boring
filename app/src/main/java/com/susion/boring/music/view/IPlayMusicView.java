package com.susion.boring.music.view;

/**
 * Created by susion on 17/1/23.
 */
public interface IPlayMusicView {
    void initMusicInfoUI();
    void initProgressView();
    void updateProgress();
    void startMusic();
    void stopMusic();
    void startMusicPlayAnimation();
    void stopMusicPlayAnimation();
    void changPlayModule();
}
