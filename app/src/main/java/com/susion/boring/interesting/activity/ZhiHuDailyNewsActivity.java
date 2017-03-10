package com.susion.boring.interesting.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.base.OnLastItemVisibleListener;
import com.susion.boring.base.QuickPageAdapter;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.interesting.adapter.ZhiHuDailyAdapter;
import com.susion.boring.interesting.contract.ZhiHuDailyContract;
import com.susion.boring.interesting.contract.presenter.ZhiHuDailyNewsPresenter;
import com.susion.boring.interesting.model.Banner;
import com.susion.boring.interesting.model.DailyNews;
import com.susion.boring.interesting.view.BannerView;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;
import com.susion.boring.view.SToolBar;

import java.util.ArrayList;
import java.util.List;

public class ZhiHuDailyNewsActivity extends BaseActivity implements ZhiHuDailyContract.View, OnLastItemVisibleListener{

    private static final int PERCENTAGE_TO_CHANGE_COLOR = 50;
    private ViewPager mViewPager;
    private LoadMoreRecycleView mRv;
    private List<BannerView> mBannerViews;
    private List<Object> mNews = new ArrayList<>();

    private int mPage = 0;
    private ZhiHuDailyContract.Presenter mPresenter;
    private AppBarLayout mAppBarLayout;
    private int mMaxScrollSize;

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, ZhiHuDailyNewsActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void setStatusBar() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhi_hu_daily_news;
    }

    @Override
    public void findView() {
        mPresenter = new ZhiHuDailyNewsPresenter(this);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mRv = (LoadMoreRecycleView) findViewById(R.id.list_view);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
    }

    @Override
    public void initView() {
        mToolBar.setTitle("今日新闻");
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolBar.getBackground().setAlpha(0);


        mRv.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));
        mRv.setAdapter(new ZhiHuDailyAdapter(this, mNews));
        mRv.setOnLastItemVisibleListener(this);
        mRv.addItemDecoration(RVUtils.getItemDecorationDivider(this, R.color.red_divider, 1, -1, UIUtils.dp2Px(15)));

    }

    @Override
    public void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (mMaxScrollSize == 0)
                    mMaxScrollSize = appBarLayout.getTotalScrollRange();

                int currentScrollPercentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;
                int alpha = (int) (currentScrollPercentage  * 1.0f / 100 * 255);
                mToolBar.getBackground().setAlpha(alpha);
//                getWindow().sets
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.loadData(mPage);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setDataForViewPage(List<Banner> banners) {
        mBannerViews = mPresenter.getBannerViews(banners);
        mViewPager.setAdapter(new QuickPageAdapter<>(mBannerViews));
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void addNewsData(List<DailyNews.StoriesBean> news) {
        mNews.addAll(news);
        mRv.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLastItemVisible() {
        mPage++;
        mPresenter.loadData(mPage);
    }
}

