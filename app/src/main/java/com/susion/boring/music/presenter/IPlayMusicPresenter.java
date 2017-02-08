package com.susion.boring.music.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by susion on 17/2/8.
 */
public interface IPlayMusicPresenter extends IMediaPlayPresenter{
    Drawable getBackgroundBlurImage(Bitmap bitmap);
}
