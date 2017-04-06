package com.susion.boring.read.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.read.mvp.contract.ZhiHuEssayContract;
import com.susion.boring.read.mvp.entity.NewsDetail;
import com.susion.boring.read.mvp.entity.ZhiHuEssay;
import com.susion.boring.read.mvp.presenter.ZhiHuEssayPresenter;
import com.susion.boring.utils.UIUtils;

import java.util.Date;

public class ZhiHuEssayActivity extends BaseActivity implements ZhiHuEssayContract.View, View.OnClickListener {
    private static final String ESSAY_ID = "ESSAY_ID";
    private static final String ESSAY_DATE = "ESSAY_DATE";
    private ZhiHuEssay mEssay;
    private WebView mWebView;
    private SimpleDraweeView mSdvNewsHeader;
    private ImageView mIvPre;
    private ImageView mIvNext;
    private ImageView mIvLike;

    private ZhiHuEssayContract.Presenter mPresenter;
    private NestedScrollView mWbParent;
    private AppBarLayout mAppBarLayout;


    public static void start(Context mContext, String id, Date date) {
        Intent intent = new Intent(mContext, ZhiHuEssayActivity.class);
        intent.putExtra(ESSAY_ID, id);
        intent.putExtra(ESSAY_DATE, date);
        mContext.startActivity(intent);
    }

    @Override
    protected void setStatusBar() {
        UIUtils.expandContentLayoutFullScreen(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhi_hu_essay;
    }

    @Override
    public void findView() {
        initMVP();
        mWbParent = (NestedScrollView) findViewById(R.id.ac_zhi_hu_essay_wv_parent);
        mSdvNewsHeader = (SimpleDraweeView) findViewById(R.id.ac_zhi_hu_essay_sdv_news_header);
        mIvPre = (ImageView) findViewById(R.id.ac_zhi_hu_essay_iv_pre);
        mIvNext = (ImageView) findViewById(R.id.ac_zhi_hu_essay_iv_next);
        mIvLike = (ImageView) findViewById(R.id.ac_zhi_hu_essay_iv_lile);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
    }

    private void initMVP() {
        mEssay = new ZhiHuEssay();
        mEssay.id = getIntent().getStringExtra(ESSAY_ID);
        Date date = (Date) getIntent().getSerializableExtra(ESSAY_DATE);
        DbBaseOperate<NewsDetail> dbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), this, NewsDetail.class);
        mPresenter = new ZhiHuEssayPresenter(this, date, dbOperator);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initListener() {
        mIvPre.setOnClickListener(this);
        mIvLike.setOnClickListener(this);
        mIvNext.setOnClickListener(this);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            public static final int PERCENTAGE_TO_SHOW_IMAGE = 90;
            private int mMaxScrollSize;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (mMaxScrollSize == 0)
                    mMaxScrollSize = appBarLayout.getTotalScrollRange();

                int currentScrollPercentage = ((Math.abs(verticalOffset)) * 100
                        / mMaxScrollSize);

                if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
                    getWindow().setStatusBarColor(UIUtils.getColor(ZhiHuEssayActivity.this, R.color.colorPrimary));
                } else {
                    getWindow().setStatusBarColor(Color.TRANSPARENT);
                }

            }
        });
    }

    @Override
    public void initData() {
        mPresenter.loadEssay(mEssay.id);
    }

    @Override
    public void showEssayContent(String contentHtml) {
        if (mWbParent.getChildCount() > 0) {
            mWbParent.removeViewAt(0);
        }
        mWebView = new WebView(this);
        mWebView.loadDataWithBaseURL(null, contentHtml, "text/html", "utf-8", null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWbParent.addView(mWebView, layoutParams);
    }

    @Override
    public void setHeaderImage(String url) {
        mSdvNewsHeader.setImageURI(url);
    }

    @Override
    public void refreshLikeStatus(boolean status) {
        UIUtils.refreshLikeStatus(mIvLike, status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ac_zhi_hu_essay_iv_pre:
                mPresenter.loadPreEssay();
                break;

            case R.id.ac_zhi_hu_essay_iv_next:
                mPresenter.loadNextEssay();
                break;

            case R.id.ac_zhi_hu_essay_iv_lile:
                mPresenter.likeEssay();
                break;
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
