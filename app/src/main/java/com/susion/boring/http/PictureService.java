package com.susion.boring.http;

import com.susion.boring.interesting.mvp.model.PictureCategoryResult;
import com.susion.boring.interesting.mvp.model.SimplePictureList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by susion on 17/3/17.
 */
public interface PictureService {

    @GET(BaseURL.PICTURE_GET_TYPE)
    Observable<SimplePictureList> getPicturesByType(@Query("type") String type, @Query("page") String page);

    @GET(BaseURL.PICTURE_GET_CLASS)
    Observable<PictureCategoryResult> getPictureCategory();
}
