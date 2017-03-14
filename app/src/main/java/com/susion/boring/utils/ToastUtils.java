package com.susion.boring.utils;

import android.widget.Toast;

import com.susion.boring.base.SAppApplication;


/**
 * Toast Util
 * Created by sam on 14-9-13.
 */
public class ToastUtils {

    private ToastUtils() {
    }

    public static void showShort(int resId) {
        Toast.makeText(SAppApplication.getAppContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(SAppApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(SAppApplication.getAppContext(), resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(SAppApplication.getAppContext(), message, Toast.LENGTH_LONG).show();
    }
}
