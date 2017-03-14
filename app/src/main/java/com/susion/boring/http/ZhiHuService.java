package com.susion.boring.http;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.susion.boring.interesting.model.DailyNews;
import com.susion.boring.interesting.model.NewsDetail;
import com.susion.boring.music.model.PlayListSong;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by susion on 17/3/9.
 */
public interface ZhiHuService {

    @GET(BaseURL.ZHI_HU_DAILY_LATEST_NEWS)
    Observable<DailyNews> getLatestNews();

    @GET(BaseURL.ZHI_HU_DAILY_FIX_DATE_NEWS+"/{date}")
    Observable<DailyNews> getFixDateNews(@Path("date") String oid);


    @GET(BaseURL.ZHI_HU_ESSAY_CONTENT+"/{id}")
    Observable<NewsDetail> getEssayContent(@Path("id") String id);

    @GET
    Observable<ResponseBody> getImage(@Url String url);

    @GET
    Observable<ResponseBody> getEssayCSS(@Url String url);
}
