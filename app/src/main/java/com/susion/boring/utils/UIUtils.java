package com.susion.boring.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by susion on 17/1/17.
 */
public class UIUtils {

    private static DisplayMetrics sMetrics;

    private static DisplayMetrics getDisplayMetrics() {
        if (sMetrics == null) {
            sMetrics = Resources.getSystem().getDisplayMetrics();
        }
        return sMetrics;
    }

    public static int px2Dp(float px){
        final float scale = getDisplayMetrics() != null ? getDisplayMetrics().density : 1;
        return (int) (px / scale + 0.5f);
    }

    public static int dp2Px(float dp){
        final float scale = getDisplayMetrics() != null ? getDisplayMetrics().density : 1;
        return (int) (dp * scale + 0.5f);
    }

    /*设备屏幕宽度*/
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /*设备屏幕高度*/
    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    public static void startSimpleRotateAnimation(View view) {
        RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(1000);
        view.startAnimation(animation);
    }

    public static String translatePlayCount(int playCount) {
        if (playCount < 9999) {
            return playCount+"";
        }
        return playCount / 10000 +"万";
    }

    public static String getPlayListTags(List<String> tags) {
        String result = "";

        if (tags != null && !tags.isEmpty()) {
            result += "Tags: ";
            for (String tag : tags) {
                result += tag + " ";
            }
        }

        return result;
    }
}
