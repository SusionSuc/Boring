package com.susion.boring.music.model;

/**
 * Created by susion on 17/2/8.
 */
public class Lyric {
    public String version;
    public String time;
    public String lyric;
    public int pos;

    public Lyric(String time, String lyric, int pos) {
        this.time = time;
        this.lyric = lyric;
        this.pos = pos;
    }
}
