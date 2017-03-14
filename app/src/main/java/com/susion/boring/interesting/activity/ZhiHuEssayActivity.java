package com.susion.boring.interesting.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.interesting.contract.ZhiHuEssayContract;
import com.susion.boring.interesting.contract.presenter.ZhiHuEssayPresenter;

public class ZhiHuEssayActivity extends BaseActivity implements ZhiHuEssayContract.View, View.OnClickListener {

    private static final String ESSAY_ID = "ESSAY_ID";
    private String mEssayId;
    private WebView mWebView;
    private SimpleDraweeView mSdvNewsHeader;

    private ZhiHuEssayContract.Presenter mPresenter;
    private ImageView mIvPre;
    private ImageView mIvNext;
    private ImageView mIvLike;

    public static void start(Context mContext, String id) {
        Intent intent = new Intent(mContext, ZhiHuEssayActivity.class);
        intent.putExtra(ESSAY_ID, id);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhi_hu_essay;
    }

    @Override
    public void findView() {
        mEssayId = getIntent().getStringExtra(ESSAY_ID);
        mPresenter = new ZhiHuEssayPresenter(this);

        mWebView = (WebView) findViewById(R.id.ac_zhi_hu_essay_wv_content);
        mSdvNewsHeader = (SimpleDraweeView) findViewById(R.id.ac_zhi_hu_essay_sdv_news_header);
        mIvPre = (ImageView) findViewById(R.id.ac_zhi_hu_essay_iv_pre);
        mIvNext = (ImageView) findViewById(R.id.ac_zhi_hu_essay_iv_next);
        mIvLike = (ImageView) findViewById(R.id.ac_zhi_hu_essay_iv_lile);
    }


    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        mIvPre.setOnClickListener(this);
        mIvLike.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mPresenter.loadEssay(mEssayId);
    }

    @Override
    public void showEssayContent(String contentHtml) {
        mWebView.loadData(contentHtml, "text/html; charset=UTF-8", null);
    }

    @Override
    public void setHeaderImage(String url) {
        mSdvNewsHeader.setImageURI(url);
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
                mPresenter.likeEssay(mEssayId);
                break;
        }
    }


}
