package com.susion.boring.interesting.extras;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.susion.boring.http.APIHelper;

import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/12.
 */
public class RXImageGetter implements Html.ImageGetter {
    DrawableWrapper mWrapper;
    TextView mTvHtml;

    public RXImageGetter(TextView mTvHtml) {
        this.mTvHtml = mTvHtml;
    }

    @Override
    public Drawable getDrawable(String source) {
        mWrapper = new DrawableWrapper();

        APIHelper.getZhiHuService()
                .getImage(source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap call(ResponseBody responseBody) {
                        InputStream is;
                        is = responseBody.byteStream();
                        if (is != null) {
                            return BitmapFactory.decodeStream(is);
                        }
                        return null;
                    }
                }).subscribe(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Bitmap bitmap) {

                BitmapDrawable drawable = new BitmapDrawable(null, bitmap);
                float scale = getScale(drawable);
                drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * scale), (int) (drawable.getIntrinsicHeight() * scale));
                mWrapper.drawable = drawable;

                mTvHtml.invalidate();
                mTvHtml.setText(mTvHtml.getText());
            }
        });

        return mWrapper;
    }

    public class DrawableWrapper extends BitmapDrawable {
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    private float getScale(Drawable drawable) {
        float maxWidth = mTvHtml.getWidth();
        float originalDrawableWidth = drawable.getIntrinsicWidth();
        return maxWidth / originalDrawableWidth;
    }
}
