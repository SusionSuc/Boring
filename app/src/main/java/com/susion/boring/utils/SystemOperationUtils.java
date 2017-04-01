package com.susion.boring.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.susion.boring.base.ui.SplashActivity;

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

    public static boolean hasShortcut(Activity activity, String shortcutName) {
        if (queryLaunchHasShortcut(activity, shortcutName)) {
            return true;
        }

        if (SPUtils.getBooleanFromConfig(SPUtils.KEY_SHORTCUT)) {
            return true;
        }

        return false;
    }

    public static void createShortCut(Activity activity, String shortcutName, int logoId) {
        SPUtils.setBooleanConfig(SPUtils.KEY_SHORTCUT, true);

        Intent intent = new Intent();
        intent.setClass(activity, activity.getClass());
        /*以下两句是为了在卸载应用的时候同时删除桌面快捷方式*/
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        shortcutIntent.putExtra("duplicate", false);
        //需要现实的名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(activity.getApplicationContext(), logoId);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //点击快捷图片，运行的程序主入口
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        //发送广播。OK
        activity.sendBroadcast(shortcutIntent);
    }


    public static boolean queryLaunchHasShortcut(Activity activity, String shortcutName) {
        String url = "content://com.android.launcher2.settings/favorites?notify=true";
        ContentResolver resolver = activity.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(url), null, "title=?", new String[]{shortcutName}, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }


    public static void setWebViewSetting(WebView mWv) {
        mWv.getSettings().setJavaScriptEnabled(true);
        mWv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWv.getSettings().setSupportMultipleWindows(true);
        mWv.setWebViewClient(new WebViewClient());
        mWv.setWebChromeClient(new WebChromeClient());
    }
}
