package com.susion.boring.music.presenter;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by susion on 17/1/23.
 */
public interface IMediaPlayPresenter {

    void initMediaPlayer(String mediaUri, boolean autoPlay) throws Exception;
    boolean startPlay();
    void pausePlay();
    void stopPlay();
    void releaseResource();
    void seekTo(int pos);
}
