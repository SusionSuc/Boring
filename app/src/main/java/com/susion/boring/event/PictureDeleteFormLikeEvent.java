package com.susion.boring.event;

import com.susion.boring.read.mvp.entity.SimplePicture;

/**
 * Created by susion on 17/3/29.
 */
public class PictureDeleteFormLikeEvent {
    public SimplePicture picture;

    public PictureDeleteFormLikeEvent(SimplePicture data) {
        picture = data;
    }
}
