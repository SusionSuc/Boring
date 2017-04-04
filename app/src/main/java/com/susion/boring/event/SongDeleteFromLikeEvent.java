package com.susion.boring.event;

import com.susion.boring.music.mvp.model.SimpleSong;

/**
 * Created by susion on 17/4/4.
 */
public class SongDeleteFromLikeEvent {
    public SimpleSong song;

    public SongDeleteFromLikeEvent(SimpleSong song) {
        this.song = song;
    }
}
