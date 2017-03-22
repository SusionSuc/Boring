package com.susion.boring.interesting.mvp.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.base.ui.mainui.MainActivity;
import com.susion.boring.interesting.view.DrawScaleImageView;

public class PictureViewActivity extends BaseActivity {

    private static final String IMAGE_URL = "image_url";
    private static final String ORIGIN_IMAGE_POS = "origin_image_pos";
    private DrawScaleImageView mDrawerScaleIv;
    private String mImageURL;
    private Rect mOriginIvPos;

    public static void start(Activity ac, String big, Rect imageViewPos) {
        Intent intent = new Intent();
        intent.putExtra(IMAGE_URL, big);
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
        mImageURL = getIntent().getStringExtra(IMAGE_URL);
        mOriginIvPos = getIntent().getParcelableExtra(ORIGIN_IMAGE_POS);

        mDrawerScaleIv = (DrawScaleImageView) findViewById(R.id.draw_scale_iv);
    }

    @Override
    public void initView() {
        mDrawerScaleIv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mDrawerScaleIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mDrawerScaleIv.setImageURI(mImageURL);
                mDrawerScaleIv.startEntryScaleAnimation(mOriginIvPos);
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
