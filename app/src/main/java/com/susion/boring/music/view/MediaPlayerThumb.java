package com.susion.boring.music.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.susion.boring.R;

/**
 * Created by susion on 16/11/9.
 */
public class MediaPlayerThumb {

    private static final double CIRCLE1_PADDING_PERCENT = 0.8;
    private static final double CIRCLE2_PADDING_PERCENT = 0.5;
    int width;
    int height;

    private Bitmap bitmap;
    private Drawable drawable;


    public MediaPlayerThumb(int height, int width, Context mContext) {
        this.height = height;
        this.width = width;

        int circle1Padding = (int) (width * CIRCLE1_PADDING_PERCENT);
        int circle2Padding = (int) (width * CIRCLE2_PADDING_PERCENT);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        RectF ovalRect1 = new RectF(0, 0, width, height);
        paint.setColor(mContext.getResources().getColor(R.color.transparent));
        paint.setStyle(Paint.Style.FILL);

        RectF ovalRect2 = new RectF(circle1Padding, circle1Padding, width - circle1Padding, height - circle1Padding);
        paint.setColor(mContext.getResources().getColor(R.color.thumb_out));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(ovalRect2, paint);

        RectF ovalRect3 = new RectF(circle2Padding, circle2Padding, width - circle2Padding, height - circle2Padding);
        paint.setColor(mContext.getResources().getColor(R.color.white));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(ovalRect3, paint);
        canvas.save();
        drawable = new BitmapDrawable(mContext.getResources(), bitmap);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Drawable getDrawable() {
        return drawable;
    }


}
