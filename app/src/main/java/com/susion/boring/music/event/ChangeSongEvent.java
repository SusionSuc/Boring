package com.susion.boring.music.event;

import com.susion.boring.music.model.Song;

/**
 * Created by susion on 17/3/6.
 */
public class ChangeSongEvent {

    public  Song song;

    public ChangeSongEvent(Song mData) {
        song = mData;
    }
}
