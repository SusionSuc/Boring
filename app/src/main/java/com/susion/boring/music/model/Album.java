package com.susion.boring.music.model;

import java.io.Serializable;

/**
 * Created by susion on 17/1/20.
 */
public class Album implements Serializable{
    public String id;
    public String name;
    public Singer artist;
    public String picUrl;

    public Album(String name) {
        this.name = name;
    }
}
