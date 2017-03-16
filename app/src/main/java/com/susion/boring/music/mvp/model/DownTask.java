package com.susion.boring.music.mvp.model;

import java.util.Map;

/**
 * Created by susion on 17/2/17.
 */
public class DownTask {
    public static final int DOWN_ERROR = 1;
    public static final int DOWN_SUCCESS = 2;
    public String uri;
    public Map<String, String> params;
    public String taskName;

    public long currentDownSize;
    public long totalSize;
    public float currentProgress;
    public long networkSpeed;
    public int downStatus;

    public DownTask(String uri) {
        this.uri = uri;
    }

    public void setDownInfo(long currentSize, long totalSize, float progress, long networkSpeed){
        this.currentDownSize = currentSize;
        this.totalSize = totalSize;
        this.networkSpeed = networkSpeed;
        this.currentProgress = progress;
    }
}
