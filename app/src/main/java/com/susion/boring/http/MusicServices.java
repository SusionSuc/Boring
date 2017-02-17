package com.susion.boring.http;


import com.susion.boring.music.model.LyricResult;
import com.susion.boring.music.model.MusicSearchResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by susion on 17/1/20.
 */
public interface MusicServices {

    @GET(BaseURL.MUSIC+"/search/get/")
    Observable<MusicSearchResult> searchMusic(@Query("s") String musicName, @Query("limit") int limit,
                                              @Query("type") int type, @Query("offset") int page);

    @GET(BaseURL.MUSIC_SEARCH_LYRIC+"/api/song/lyric?os=pc&kv=-1&tv=-1&lv=-1")
    Observable<LyricResult> getMusicLyric(@Query("id") String songId);

    @GET
    Call<ResponseBody> downMusic(@Url String fileUrl);
}
