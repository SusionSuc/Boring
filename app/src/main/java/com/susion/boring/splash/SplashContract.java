package com.susion.boring.splash;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.susion.boring.base.BasePresenter;
import com.susion.boring.base.BaseView;
import com.susion.boring.music.model.Song;

import java.util.List;

/**
 * Created by susion on 17/2/20.
 */
public interface SplashContract {

    interface View extends BaseView<Presenter> {
        void setCenterImageAndDescText(int imageId, int textId);

        void setAuthorInfo(Drawable leftImage, int textId);
    }

    interface Presenter extends BasePresenter {
        void setImageAndDescText(int imageId, int textId);

        void skipToMainActivity(Activity context, android.view.View view);

        void initConfig();

        void setAuthorInfo(Context context, int iconId, int textId);

        void requestPermission(Activity activity);

    }
}
