package com.susion.boring.event;

import com.susion.boring.music.mvp.model.PlayList;

/**
 * Created by susion on 17/4/4.
 */
public class PlayListDeleteFromLikeEvent {
    public PlayList playList;
    public PlayListDeleteFromLikeEvent(PlayList mPlayList) {
        playList = mPlayList;
    }
}
