package com.susion.boring.music.presenter;

/**
 * Created by susion on 17/1/23.
 */
public interface IPlayMusicPresenter {

    void startPlayMusic(String musicUri);
    void stopPlayMusic();
    void reStartPlayMusic();
    void preMusic();
    void nextMusic();

}
