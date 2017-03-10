package com.susion.boring.http;

import com.susion.boring.interesting.model.DailyNews;
import com.susion.boring.music.model.PlayListSong;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by susion on 17/3/9.
 */
public interface ZhiHuService {

    @GET(BaseURL.ZHI_HU_DAILY_LATEST_NEWS)
    Observable<DailyNews> getLatestNews();
}
