package com.susion.boring.API;

import android.graphics.Bitmap;

import rx.Observable;

/**
 * Created by susion on 17/1/25.
 */
public interface ImageService {
    Observable<Bitmap> searchMusic();
}
