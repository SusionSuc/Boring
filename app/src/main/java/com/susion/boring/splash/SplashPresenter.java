package com.susion.boring.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.susion.boring.mainui.MainActivity;
import com.susion.boring.utils.UIUtils;
import com.yanzhenjie.permission.AndPermission;

/**
 * Created by susion on 17/1/17.
 */
public class SplashPresenter implements SplashContract.Presenter{

    SplashContract.View mSplashView;

    public SplashPresenter(SplashContract.View mSplashView) {
        this.mSplashView = mSplashView;
    }

    @Override
    public void setImageAndDescText(int imageId, int textId) {
        mSplashView.setCenterImageAndDescText(imageId, textId);
    }

    @Override
    public void skipToMainActivity(final Activity context, View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(context);
                context.finish();
            }
        }, 3000);
    }


    @Override
    public void initConfig() {

    }

    /*
    * -1 means no icon
    * */
    @Override
    public void setAuthorInfo(Context context, int iconId, int textId) {
        if (iconId != -1) {
            Drawable drawable = context.getResources().getDrawable(iconId);
            drawable.setBounds(0, 0, UIUtils.dp2Px(20), UIUtils.dp2Px(20));
            mSplashView.setAuthorInfo(drawable, textId);
            return;
        }

        mSplashView.setAuthorInfo(null, textId);
    }

    @Override
    public void requestPermission(Activity activity) {
        AndPermission.with(activity)
                .requestCode(100)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET, Manifest.permission.MEDIA_CONTENT_CONTROL, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                .send();
    }

}
