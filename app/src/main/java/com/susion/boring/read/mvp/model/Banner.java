package com.susion.boring.read.mvp.model;

/**
 * Created by susion on 17/3/9.
 */
public class Banner {
    public String imgUrl;
    public String title;
    public String newsID;

    public Banner(String imgUrl, String newsID, String title) {
        this.imgUrl = imgUrl;
        this.newsID = newsID;
        this.title = title;
    }
}
