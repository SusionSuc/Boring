package com.susion.boring.music.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.music.view.IPlayMusicView;
import com.susion.boring.utils.FastBlurUtil;
import com.susion.boring.utils.ImageUtils;

/**
 * Created by susion on 17/1/25.
 */
public class PlayMusicPresenter implements IPlayMusicPresenter {

    private IPlayMusicView mView;
    private Context mContext;

    public PlayMusicPresenter(IPlayMusicView mView) {
        this.mView = mView;
    }
    public PlayMusicPresenter(IPlayMusicView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void startPlayMusic(String musicUri) {

    }

    @Override
    public void stopPlayMusic() {

    }

    @Override
    public void reStartPlayMusic() {

    }

    @Override
    public void preMusic() {

    }

    @Override
    public void nextMusic() {

    }

    @Override
    public void setBackground(String imageUri, final ViewGroup parent, Context context) {
        ImageUtils.LoadImage(context, imageUri, new ImageUtils.OnLoadFinishLoadImage() {
            @Override
            public void loadImageFinish(String imageUri, View view, Bitmap loadedImage) {
                loadedImage = loadedImage.copy(loadedImage.getConfig(), true);
                parent.setBackground(new BitmapDrawable(FastBlurUtil.doBlur(loadedImage, 200, true)));
            }
        });
    }

}
