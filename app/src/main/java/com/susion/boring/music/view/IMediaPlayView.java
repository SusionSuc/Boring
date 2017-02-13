package com.susion.boring.music.view;

import android.media.MediaPlayer;

/**
 * Created by susion on 17/1/23.
 */
public interface IMediaPlayView {
    void updateBufferedProgress(int percent);
    void updatePlayProgress(int curPos, int duration);
    void preparedPlay(int duration);
    void completionPlay();

}
