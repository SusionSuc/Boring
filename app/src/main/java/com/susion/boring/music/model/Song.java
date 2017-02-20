package com.susion.boring.music.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by susion on 17/1/20.
 */
public class Song implements Serializable{
    public String id;
    public String name;
    public List<Singer> artists;
    public Album album;
    public String audio;
    public String djProgramId;
    public String page;


    //local music info from media database
    public String title;
    public int duration;
    public long size;
    public String artist;
    public String url;
    public long localMusicId;
}
