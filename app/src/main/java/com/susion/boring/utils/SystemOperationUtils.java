package com.susion.boring.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by susion on 17/2/7.
 */
public class SystemOperationUtils {

    private static String sVersionName;

    public static void closeSystemKeyBoard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }

    public static String getAppVersion(Context context) {
        try {
            if (TextUtils.isEmpty(sVersionName)) {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                sVersionName = info.versionName;
            }
        } catch (Exception e) {
        }
        return sVersionName;
    }
}
