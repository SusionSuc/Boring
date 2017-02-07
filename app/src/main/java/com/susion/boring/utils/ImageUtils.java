package com.susion.boring.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by susion on 17/1/26.
 */
public class ImageUtils {

    public static void LoadImage(Context context, String uri, final OnLoadFinishLoadImage listener){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(context);
        ImageLoader load = ImageLoader.getInstance();
        load.init(configuration);

        load.loadImage(uri, new SimpleImageLoadingListener(){

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (listener == null) {
                    return;
                }
                listener.loadImageFinish(imageUri, view, loadedImage);
            }
        });
    }

    public interface OnLoadFinishLoadImage{
        void loadImageFinish(String imageUri, View view, Bitmap loadedImage);

    }
}
