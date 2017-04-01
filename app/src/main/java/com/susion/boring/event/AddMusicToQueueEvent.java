package com.susion.boring.event;

import com.susion.boring.music.mvp.model.Song;

/**
 * Created by susion on 17/4/1.
 */
public class AddMusicToQueueEvent {
    public Song song;
    public AddMusicToQueueEvent(Song song) {
        this.song = song;
    }
}
