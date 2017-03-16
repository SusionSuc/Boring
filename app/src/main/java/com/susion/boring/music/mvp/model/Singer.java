package com.susion.boring.music.mvp.model;

import java.io.Serializable;

/**
 * Created by susion on 17/1/20.
 */
public class Singer implements Serializable{
    public String id;
    public String name;
    public String picUrl;

    public Singer(String name) {
        this.name = name;
    }
}
