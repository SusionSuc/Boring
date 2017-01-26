package com.susion.boring.http;

import android.graphics.Bitmap;

import com.susion.boring.music.model.MusicSearchResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by susion on 17/1/25.
 */
public interface ImageService {
    Observable<Bitmap> searchMusic();
}
