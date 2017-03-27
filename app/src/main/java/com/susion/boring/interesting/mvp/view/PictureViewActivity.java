package com.susion.boring.interesting.mvp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.interesting.mvp.model.SimplePicture;
import com.susion.boring.interesting.view.DrawScaleImageView;
import com.susion.boring.utils.ToastUtils;
import com.susion.boring.utils.UIUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PictureViewActivity extends BaseActivity {
    private static final String IMAGE_URL = "image_url";
    private static final String ORIGIN_IMAGE_POS = "origin_image_pos";
    private DrawScaleImageView mDrawerScaleIv;
    private SimplePicture mPicture;
    private View mRlPictureOperator;
    private ImageView mIvLove;
    private ImageView mIvDown;

    private boolean mPicOperatorShow;
    private DbBaseOperate<SimplePicture> mDbOperator;

    public static void start(Activity ac, SimplePicture image, Rect imageViewPos) {
        Intent intent = new Intent();
        intent.putExtra(IMAGE_URL, image);
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
        mDbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), this, SimplePicture.class);

        mPicture = (SimplePicture) getIntent().getSerializableExtra(IMAGE_URL);
        mDrawerScaleIv = (DrawScaleImageView) findViewById(R.id.ac_picture_iv_draw_scale);
        mRlPictureOperator = findViewById(R.id.ac_picture_rl_picture_operator);
        mIvLove = (ImageView) findViewById(R.id.ac_picture_iv_love);
        mIvDown = (ImageView) findViewById(R.id.ac_picture_iv_download);
    }

    @Override
    public void initView() {
        mRlPictureOperator.setVisibility(View.INVISIBLE);
        mDrawerScaleIv.setImageURI(mPicture.getBig());
        mDrawerScaleIv.setScaleListener(new DrawScaleImageView.DrawScaleImageViewListener() {
            @Override
            public void onScaleChange(int alpha) {
                mRlPictureOperator.setVisibility(View.GONE);
                ((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0).setBackgroundColor(Color.argb(alpha, 0, 0, 0));
            }

            @Override
            public void onExitViewImage() {
                PictureViewActivity.this.finish();
            }

            @Override
            public void onClickImage() {
                mPicOperatorShow = !mPicOperatorShow;
                mRlPictureOperator.setVisibility(mPicOperatorShow ? View.VISIBLE : View.INVISIBLE);
            }
        });

        mIvLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPicture.favorite = !mPicture.favorite;
                mDbOperator.add(mPicture);
                UIUtils.refreshLikeStatus(mIvLove, mPicture.favorite);
            }
        });

        mIvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
