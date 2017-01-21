package com.susion.boring.splash;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by susion on 17/1/17.
 */
public interface ISplashPresenter {
    public void setImageAndDescText(int imageId, int textId);
    public void skipToMainActivity(Activity context, View view);
    public void initConfig();
    public void setAuthorInfo(Context context, int iconId, int textId);
}
