package com.susion.boring.music.mvp.model;

/**
 * Created by susion on 17/2/8.
 */
public class Lyric {
    public static int NO_MEASURE = -1;

    public String version;
    public String time;
    public String lyric;
    public int pos;
    public int height = NO_MEASURE;

    public Lyric(String time, String lyric, int pos) {
        this.time = time;
        this.lyric = lyric;
        this.pos = pos;
    }
}
