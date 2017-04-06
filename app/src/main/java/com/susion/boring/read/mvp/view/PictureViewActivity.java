package com.susion.boring.read.mvp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.event.PictureDeleteFormLikeEvent;
import com.susion.boring.http.APIHelper;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.read.mvp.entity.SimplePicture;
import com.susion.boring.read.view.DrawScaleImageView;
import com.susion.boring.utils.FileUtils;
import com.susion.boring.utils.ToastUtils;
import com.susion.boring.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;

public class PictureViewActivity extends BaseActivity {
    private static final String IMAGE_URL = "image_url";
    private static final String ORIGIN_IMAGE_POS = "origin_image_pos";
    private DrawScaleImageView mDrawerScaleIv;
    private SimplePicture mPicture;
    private View mRlPictureOperator;
    private ImageView mIvLove;
    private ImageView mIvDown;

    private DbBaseOperate<SimplePicture> mDbOperator;
    private boolean mIsLove;

    public static void start(Activity ac, SimplePicture image, Rect imageViewPos) {
        Intent intent = new Intent();
        intent.putExtra(IMAGE_URL, image);
        intent.putExtra(ORIGIN_IMAGE_POS, imageViewPos);
        intent.setClass(ac, PictureViewActivity.class);
        ac.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture_view;
    }

    @Override
    protected void setStatusBar() {
        UIUtils.expandContentLayoutFullScreen(this);
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
        mDrawerScaleIv.setImageURI(mPicture.getBig());
        if (TextUtils.isEmpty(mPicture.id)) {
            mPicture.id = mPicture.getBig();
        }
        checkIsLiked();
    }

    private void checkIsLiked() {
        APIHelper.subscribeSimpleRequest(mDbOperator.query(mPicture.id), new CommonObserver<SimplePicture>() {
            @Override
            public void onNext(SimplePicture simplePicture) {
                mIsLove = simplePicture != null ? true : false;
                UIUtils.refreshLikeStatus(mIvLove, mIsLove);
            }
        });
    }

    @Override
    public void initListener() {
        mDrawerScaleIv.setScaleListener(new DrawScaleImageView.DrawScaleImageViewListener() {
            @Override
            public void onScaleChange(int alpha) {
                mRlPictureOperator.setVisibility(View.INVISIBLE);
                ((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0).setBackgroundColor(Color.argb(alpha, 0, 0, 0));
            }

            @Override
            public void onExitViewImage() {
                PictureViewActivity.this.finish();
            }

            @Override
            public void onRestoreImageState() {
                mRlPictureOperator.setVisibility(View.VISIBLE);
            }
        });

        mIvLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsLove) {
                    APIHelper.subscribeSimpleRequest(mDbOperator.delete(mPicture), new CommonObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean flag) {
                            ToastUtils.showShort(flag ? "已经从喜欢列表移除" : "从喜欢列表移除失败");
                            mIsLove = flag ? false : true;
                            UIUtils.refreshLikeStatus(mIvLove, mIsLove);
                            if (!mIsLove) {
                                EventBus.getDefault().post(new PictureDeleteFormLikeEvent(mPicture));
                            }
                        }
                    });
                } else {
                    APIHelper.subscribeSimpleRequest(mDbOperator.add(mPicture), new CommonObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean flag) {
                            ToastUtils.showShort(flag ? "已喜欢" : "喜欢失败");
                            mIsLove = flag ? true : false;
                            UIUtils.refreshLikeStatus(mIvLove, mIsLove);
                        }
                    });
                }
            }
        });

        mIvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIHelper.subscribeSimpleRequest(APIHelper.getPictureService().getImage(mPicture.getMiddle()),
                        new CommonObserver<ResponseBody>() {
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                ToastUtils.showShort("下载图片失败");
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                if (FileUtils.saveImage(responseBody.byteStream(), mPicture.getMiddle())) {
                                    ToastUtils.showShort("图片已经保存");
                                } else {
                                    ToastUtils.showShort("图片保存失败");
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void initData() {

    }

}
