package com.susion.boring.music.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.susion.boring.R;
import com.susion.boring.music.view.IPlayMusicView;
import com.susion.boring.utils.FastBlurUtil;

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
//        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
//                .createDefault(context);
//        ImageLoader load = ImageLoader.getInstance();
//        load.init(configuration);
//        load.loadImage(imageUri, new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                loadedImage = loadedImage.copy(loadedImage.getConfig(), true);
//                parent.setBackground(new BitmapDrawable(FastBlurUtil.doBlur(loadedImage, 100, true)));
//            }
//        });

    }
}
