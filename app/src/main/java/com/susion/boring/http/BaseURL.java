package com.susion.boring.http;

/**
 * Created by susion on 17/1/20.
 */
public interface BaseURL {
    //music
    String MUSIC_SEARCH = "https://api.imjad.cn/cloudmusic";
    String MUSIC_SEARCH_LYRIC = "http://music.163.com";
    String CLOUD_MUSIC_API_MUSICINGO = "http://musicapi.duapp.com/api.php";
    String MUSIC_PLAY_LIST = "http://musicapi.duapp.com/api.php";
    String MUSIC_PLAY_LIST_DETAIL = "https://api.imjad.cn/cloudmusic";

    //zhi hu
    String ZHI_HU_DAILY_LATEST_NEWS = "http://news-at.zhihu.com/api/4/news/latest";
    String ZHI_HU_DAILY_FIX_DATE_NEWS = "http://news-at.zhihu.com/api/4/news/before";
    String ZHI_HU_ESSAY_CONTENT = "http://news-at.zhihu.com/api/4/news";

    //picture
    String PICTURE_GET_CLASS = "http://route.showapi.com/852-1?showapi_sign=7a0e1eaf10af43c0a30ba74319a6d3fd&showapi_appid=33839";
    String PICTURE_GET_TYPE = "http://route.showapi.com/852-2?showapi_sign=7a0e1eaf10af43c0a30ba74319a6d3fd&showapi_appid=33839";

    //app info
    String BORING_GIT = "https://github.com/SusionSuc/Boring";
    String WEIBO = "http://weibo.com/3914031613/profile";
}
