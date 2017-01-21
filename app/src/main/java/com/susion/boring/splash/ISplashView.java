package com.susion.boring.splash;

import android.graphics.drawable.Drawable;

/**
 * Created by susion on 17/1/17.
 */
public interface ISplashView {
    void setCenterImageAndDescText(int imageId, int textId);
    void setAuthorInfo(Drawable leftImage, int textId);
}