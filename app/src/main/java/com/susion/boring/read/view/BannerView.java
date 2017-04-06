package com.susion.boring.read.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.read.mvp.entity.DailyNews;
import com.susion.boring.read.mvp.view.ZhiHuEssayActivity;

import java.util.Date;

/**
 * Created by susion on 17/3/9.
 */
public class BannerView extends LinearLayout {

    private SimpleDraweeView mSdvImag;
    private TextView mTvTitle;
    private DailyNews.TopStoriesBean topNews;

    public BannerView(Context context) {
        super(context);
        initView();
    }

    public BannerView(Context context, DailyNews.TopStoriesBean news) {
        super(context);
        topNews = news;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_banner, this);
        mSdvImag = (SimpleDraweeView) findViewById(R.id.view_banner_iv_img);
        mTvTitle = (TextView) findViewById(R.id.view_banner_tv_title);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ZhiHuEssayActivity.start(getContext(), topNews.getId(), new Date());
            }
        });
    }

    public void setTitle(String title) {
        mTvTitle.setText(title + "");
    }

    public void setImgUrl(String url) {
        mSdvImag.setImageURI(url);
    }

}
