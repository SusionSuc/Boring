package com.susion.boring.interesting.mvp.model;

/**
 * Created by susion on 17/3/17.
 */
public class SimplePicture {

    private String id;
    private boolean isFavorite;
    private String big;
    private String small;
    private String middle;

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }
}
