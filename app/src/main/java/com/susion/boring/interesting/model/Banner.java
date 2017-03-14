package com.susion.boring.interesting.model;

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
