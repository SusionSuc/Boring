package com.susion.boring.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.susion.boring.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by susion on 17/1/17.
 */
public class UIUtils {

    private static DisplayMetrics sMetrics;

    private static DisplayMetrics getDisplayMetrics() {
        if (sMetrics == null) {
            sMetrics = Resources.getSystem().getDisplayMetrics();
        }
        return sMetrics;
    }

    public static int px2Dp(float px) {
        final float scale = getDisplayMetrics() != null ? getDisplayMetrics().density : 1;
        return (int) (px / scale + 0.5f);
    }

    public static int dp2Px(float dp) {
        final float scale = getDisplayMetrics() != null ? getDisplayMetrics().density : 1;
        return (int) (dp * scale + 0.5f);
    }

    /*设备屏幕宽度*/
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /*设备屏幕高度*/
    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    public static void startSimpleRotateAnimation(View view) {
        RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(1000);
        view.startAnimation(animation);
    }

    public static String translatePlayCount(int playCount) {
        if (playCount < 9999) {
            return playCount + "";
        }
        return playCount / 10000 + "万";
    }

    public static String getPlayListTags(List<String> tags) {
        String result = "";

        if (tags != null && !tags.isEmpty()) {
            result += "Tags: ";
            for (String tag : tags) {
                result += tag + " ";
            }
        }
        return result;
    }

    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int height = context.getResources().getDimensionPixelSize(Integer.parseInt(field.get(obj).toString()));
            int b = 0;
            return height;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void expandContentLayoutFullScreen(Activity activity) {
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static int getColor(Context context, int resId) {
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getResources().getColor(resId, null);
        } else {
            color = context.getResources().getColor(resId);
        }
        return color;
    }

    public static void refreshLikeStatus(ImageView imageView, boolean like) {
        if (like) {
            imageView.setImageResource(R.mipmap.ic_love);
        } else {
            imageView.setImageResource(R.mipmap.ic_un_love);
        }
    }

    public static void loadSmallPicture(SimpleDraweeView simpleDraweeView, String picPath) {
        int width = 50, height = 50;
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(TextUtils.isEmpty(picPath) ? "" : picPath))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .build();
        simpleDraweeView.setController(controller);
    }
}
