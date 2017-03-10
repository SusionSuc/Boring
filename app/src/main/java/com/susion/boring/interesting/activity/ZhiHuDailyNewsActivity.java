package com.susion.boring.interesting.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.base.OnLastItemVisibleListener;
import com.susion.boring.base.QuickPageAdapter;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.interesting.adapter.ZhiHuDailyAdapter;
import com.susion.boring.interesting.contract.ZhiHuDailyContract;
import com.susion.boring.interesting.contract.presenter.ZhiHuDailyNewsPresenter;
import com.susion.boring.interesting.inf.TitleMark;
import com.susion.boring.interesting.model.Banner;
import com.susion.boring.interesting.model.DailyNews;
import com.susion.boring.interesting.view.BannerView;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZhiHuDailyNewsActivity extends BaseActivity implements ZhiHuDailyContract.View, OnLastItemVisibleListener{

    private int mMaxScrollSize;
    private ViewPager mViewPager;
    private LoadMoreRecycleView mRv;
    private List<BannerView> mBannerViews;
    private List<TitleMark> mNews = new ArrayList<>();

    private ZhiHuDailyContract.Presenter mPresenter;
    private AppBarLayout mAppBarLayout;
    private TextView mTvTitle;

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
        mPresenter.setCurrentDate(new Date(System.currentTimeMillis()));
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mRv = (LoadMoreRecycleView) findViewById(R.id.list_view);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mTvTitle = (TextView) findViewById(R.id.textview);
    }

    @Override
    public void initView() {
        mTvTitle.setText("今日新闻");

        mRv.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));
        mRv.setAdapter(new ZhiHuDailyAdapter(this, mNews));
        mRv.setOnLastItemVisibleListener(this);
        mRv.addItemDecoration(RVUtils.getZhiHuDailyNewsDecoration(this, UIUtils.dp2Px(20), new ZhiHuDailyContract.DailyNewsStickHeader(){

            @Override
            public String getTitle(int position) {
                if (position >= mNews.size() || position == 0) {
                    return "";
                }
                return mNews.get(position).getHeaderTitle();
            }

            @Override
            public int getHeaderColor(int position) {
                if (position == 0) {
                    return getResources().getColor(R.color.transparent);
                }

                return getResources().getColor(R.color.colorPrimary);
            }

            @Override
            public void setNewTitle(String newTitle) {
                mTvTitle.setText(newTitle);
            }

            @Override
            public boolean isShowTitle(int position) {
                if (position >= mNews.size()) {
                    return false;
                }

                return mNews.get(position).isShowTitle();
            }

        }));
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
                mTvTitle.getBackground().setAlpha(alpha);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.loadData(ZhiHuDailyContract.LATEST_NEWS);
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
        mPresenter.loadData(ZhiHuDailyContract.AFTER_DAY_NEWS);
    }

}

