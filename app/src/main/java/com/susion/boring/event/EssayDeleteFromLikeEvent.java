package com.susion.boring.event;

import com.susion.boring.read.mvp.entity.NewsDetail;

/**
 * Created by susion on 17/3/31.
 */
public class EssayDeleteFromLikeEvent {
    public NewsDetail newsDetail;
    public EssayDeleteFromLikeEvent(NewsDetail detail) {
        newsDetail = detail;
    }
}
