package com.susion.boring.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

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
}
