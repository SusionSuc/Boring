package com.susion.boring.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * create by susion
 */
public class AlbumUtils {

    private static final String TAG = "AlbumUtils";

    public static Bitmap parseAlbum(String path) {
        File file = new File(path);
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        try {
            metadataRetriever.setDataSource(file.getAbsolutePath());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "parseAlbum: ", e);
        }
        byte[] albumData = metadataRetriever.getEmbeddedPicture();
        if (albumData != null) {
            return BitmapFactory.decodeByteArray(albumData, 0, albumData.length);
        }
        return null;
    }

    public static void setAlbum(final ImageView view, final String path) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = AlbumUtils.pressPicture(view, AlbumUtils.parseAlbumFromFile(new File(path)));
                subscriber.onNext(bitmap);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            view.setImageBitmap(bitmap);
                        }
                    }
                });
    }

    public static Bitmap pressPicture(ImageView view, byte[] bitmaps) {
        if (bitmaps != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length, options);
            int height = options.outHeight;
            int width = options.outWidth;

            int sampleSize = 1;
            int reqWidth = view.getWidth();
            int reqHeight = view.getHeight();

            if (height > reqHeight || width > reqWidth) {
                int heightRadio = Math.round((float) height / (float) reqHeight);
                int widthRadio = Math.round((float) width / (float) reqWidth);
                sampleSize = heightRadio < widthRadio ? heightRadio : widthRadio;
            }

            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            Bitmap rtnBitmap = BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length, options);

            return rtnBitmap;
        }
        return null;
    }

    public static byte[] parseAlbumFromFile(File file) {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        try {
            metadataRetriever.setDataSource(file.getAbsolutePath());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "parseAlbum: ", e);
        }
        return metadataRetriever.getEmbeddedPicture();
    }

}
