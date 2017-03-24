package com.susion.boring.interesting.mvp.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.base.ui.mainui.MainActivity;
import com.susion.boring.interesting.view.DrawScaleImageView;

public class PictureViewActivity extends BaseActivity {

    private static final String IMAGE_URL = "image_url";
    private static final String ORIGIN_IMAGE_POS = "origin_image_pos";
    private DrawScaleImageView mDrawerScaleIv;
    private Rect mOriginIvPos;
    private String mImageUrl;

    public static void start(Activity ac, String imageUrl, Rect imageViewPos) {
        Intent intent = new Intent();
        intent.putExtra(IMAGE_URL, imageUrl);
        intent.putExtra(ORIGIN_IMAGE_POS, imageViewPos);
        intent.setClass(ac, PictureViewActivity.class);
        ac.startActivity(intent);
    }

    @Override
    public void initTransitionAnim() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture_view;
    }

    @Override
    public void findView() {
        mImageUrl = getIntent().getStringExtra(IMAGE_URL);
        mOriginIvPos = getIntent().getParcelableExtra(ORIGIN_IMAGE_POS);
        mDrawerScaleIv = (DrawScaleImageView) findViewById(R.id.draw_scale_iv);
    }

    @Override
    public void initView() {
        mDrawerScaleIv.setImageURI(mImageUrl);
        mDrawerScaleIv.setScaleListener(new DrawScaleImageView.DrawScaleImageViewListener() {
            @Override
            public void onScaleChange(int alpha) {
                ((ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0).setBackgroundColor(Color.argb(alpha, 0, 0, 0));
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
    }


}
