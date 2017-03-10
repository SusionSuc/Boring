package com.susion.boring.interesting.model;

/**
 * Created by susion on 17/3/9.
 */
public class InterestingColumn {
    private String title;
    private String desc;
    private String contentCount;
    private String bgUrl;
    private int type;


    public InterestingColumn(String title, String bgUrl, String contentCount, String desc, int type) {
        this.title = title;
        this.bgUrl = bgUrl;
        this.contentCount = contentCount;
        this.desc = desc;
        this.type = type;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentCount() {
        return contentCount;
    }

    public void setContentCount(String contentCount) {
        this.contentCount = contentCount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }
}
