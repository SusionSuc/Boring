package com.susion.boring.music.mvp.model;

/**
 * Created by susion on 17/1/21.
 */
public class MusicPageConstantItem {
    public int iconId;
    public String item;
    public String appendDesc;
    public int type;

    public MusicPageConstantItem(int iconId, String item, int type) {
        this.iconId = iconId;
        this.item = item;
        this.type = type;
    }
}
