package com.susion.boring.music.presenter;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by susion on 17/1/23.
 */
public interface IPlayMusicPresenter {

    void startPlayMusic(String musicUri);
    void stopPlayMusic();
    void reStartPlayMusic();
    void preMusic();
    void nextMusic();

    void setBackground(String imageUri, ViewGroup view, Context context);

}
