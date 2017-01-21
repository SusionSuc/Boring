package com.susion.boring.splash;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.susion.boring.R;


public class SplashActivity extends Activity implements ISplashView {

    private ImageView mIvCenterImage;
    private TextView mTvDescText;
    private SplashPresenter mPresenter;
    private TextView mTvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);     //no status bar
        setContentView(R.layout.activity_splash);

        findView();

        mPresenter = new SplashPresenter(this);
        mPresenter.setImageAndDescText(R.mipmap.logo, R.string.splash_desc);
        mPresenter.skipToMainActivity(this, getWindow().getDecorView());
        mPresenter.setAuthorInfo(this, -1, R.string.author_info);
    }

    private void findView() {
//        mIvCenterImage = (ImageView) findViewById(R.id.ac_splash_iv_image);
        mTvDescText = (TextView) findViewById(R.id.ac_splash_tv_desc);
        mTvAuthor = (TextView) findViewById(R.id.ac_splash_tv_author);
    }

    @Override
    public void setCenterImageAndDescText(int imageId, int textId) {
//        mIvCenterImage.setImageResource(imageId);
        mTvDescText.setText(textId);
    }

    @Override
    public void setAuthorInfo(Drawable leftImage, int textId) {
        if (leftImage != null) {
            mTvAuthor.setCompoundDrawablesRelative(leftImage, null, null, null);
        }
        mTvAuthor.setText(textId);
    }

}
