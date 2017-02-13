package com.susion.boring.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by susion on 17/2/13.
 */
public class SPUtils {

    public static final String MUSIC_CONFIG = "music_config";

    public static final String MUSIC_CONFIG_LAST_PLAY_MUSIC = "last_play_music";



    private static Gson mGson;
    private SPUtils(){

    }

    public static Gson getGson(){
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    public static void writeStringToMusicConfig(String key, String value, Context c) {
        SharedPreferences mSp = c.getSharedPreferences(MUSIC_CONFIG, c.MODE_PRIVATE);
        SharedPreferences.Editor edit = mSp.edit();
        edit.putString(key, value);
        edit.commit();
    }


    public static String getStringFromMusicConfig(String key, Context c){
        SharedPreferences mSp = c.getSharedPreferences(MUSIC_CONFIG, c.MODE_PRIVATE);
        return mSp.getString(key, "");
    }
}
