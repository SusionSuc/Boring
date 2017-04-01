package com.susion.boring.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.susion.boring.base.SAppApplication;

/**
 * Created by susion on 17/2/13.
 */
public class SPUtils {

    private static final String APP_CONFIG = "susion_config";
    private static Context mContext;

    public static final String KEY_SHORTCUT = "shortcut";
    public static final String KEY_LAST_PLAY_MUSIC = "last_play_music";

    private SPUtils() {
    }

    private static SharedPreferences getMSharedPreferences() {
        if (mContext == null) {
            mContext = SAppApplication.getAppContext();
        }
        return mContext.getSharedPreferences(APP_CONFIG, mContext.MODE_PRIVATE);
    }

    public static void writeStringConfig(String key, String value) {
        SharedPreferences.Editor edit = getMSharedPreferences().edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getStringFromConfig(String key) {
        return getMSharedPreferences().getString(key, "");
    }

    public static boolean getBooleanFromConfig(String key) {
        return getMSharedPreferences().getBoolean(key, false);
    }

    public static void setBooleanConfig(String key, boolean flag) {
        SharedPreferences.Editor edit = getMSharedPreferences().edit();
        edit.putBoolean(key, flag);
        edit.commit();
    }
}
