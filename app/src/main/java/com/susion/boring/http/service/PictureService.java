package com.susion.boring.http.service;

import com.susion.boring.http.BaseURL;
import com.susion.boring.read.mvp.entity.PictureCategoryResult;
import com.susion.boring.read.mvp.entity.SimplePictureList;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by susion on 17/3/17.
 */
public interface PictureService {

    @GET(BaseURL.PICTURE_GET_TYPE)
    Observable<SimplePictureList> getPicturesByType(@Query("type") String type, @Query("page") String page);

    @GET(BaseURL.PICTURE_GET_CLASS)
    Observable<PictureCategoryResult> getPictureCategory();

    @GET
    Observable<ResponseBody> getImage(@Url String url);
}
