package com.susion.boring.interesting.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.base.ui.OnLastItemVisibleListener;
import com.susion.boring.base.adapter.QuickPageAdapter;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.interesting.adapter.ZhiHuDailyAdapter;
import com.susion.boring.interesting.mvp.contract.ZhiHuDailyContract;
import com.susion.boring.interesting.mvp.model.DailyNews;
import com.susion.boring.interesting.mvp.presenter.ZhiHuDailyNewsPresenter;
import com.susion.boring.base.mvp.model.TitleMark;
import com.susion.boring.interesting.mvp.model.Banner;
import com.susion.boring.interesting.view.BannerView;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZhiHuDailyNewsActivity extends BaseActivity implements ZhiHuDailyContract.View, OnLastItemVisibleListener {

    private int mMaxScrollSize;
    private ViewPager mViewPager;
    private LoadMoreRecycleView mRv;
    private List<BannerView> mBannerViews;
    private List<TitleMark> mNews = new ArrayList<>();

    private ZhiHuDailyContract.Presenter mPresenter;
    private AppBarLayout mAppBarLayout;
    private TextView mTvTitle;
    private LinearLayoutManager mManager;

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, ZhiHuDailyNewsActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void setStatusBar() {
        UIUtils.expandContentLayoutFullScreen(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhi_hu_daily_news;
    }

    @Override
    public void findView() {
        mPresenter = new ZhiHuDailyNewsPresenter(this);
        mPresenter.setCurrentDate(new Date(System.currentTimeMillis()));
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mRv = (LoadMoreRecycleView) findViewById(R.id.list_view);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mTvTitle = (TextView) findViewById(R.id.textview);
    }

    @Override
    public void initView() {
        mTvTitle.setText("今日新闻");

        mManager = RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(mManager);
        mRv.setAdapter(new ZhiHuDailyAdapter(this, mNews));
        mRv.setOnLastItemVisibleListener(this);
        mRv.addItemDecoration(RVUtils.getZhiHuDailyNewsDecoration(this, UIUtils.dp2Px(20), new ZhiHuDailyContract.DailyNewsStickHeader() {

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
            public boolean isShowTitle(int position) {
                if (position >= mNews.size()) {
                    return false;
                }
                return mNews.get(position).isShowTitle();
            }
        }));
        mRv.addItemDecoration(RVUtils.getItemDecorationDivider(this, R.color.red_divider, 1, -1, UIUtils.dp2Px(15)));

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pos = mManager.findFirstVisibleItemPosition();
                if (pos >= 1 && pos < mNews.size()) {
                    TitleMark preTitle = mNews.get(pos - 1);
                    TitleMark title = mNews.get(pos);

                    if (!preTitle.equals(title)) {
                        mTvTitle.setText(title.getHeaderTitle());
                    }
                }
            }
        });
    }

    @Override
    public void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (mMaxScrollSize == 0)
                    mMaxScrollSize = appBarLayout.getTotalScrollRange();

                int currentScrollPercentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;
                int alpha = (int) (currentScrollPercentage * 1.0f / 100 * 255);
                mTvTitle.getBackground().setAlpha(alpha);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.loadData(ZhiHuDailyContract.LATEST_NEWS);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void setDataForViewPage(List<DailyNews.TopStoriesBean> banners) {
        mBannerViews = mPresenter.getBannerViews(banners);
        mViewPager.setAdapter(new QuickPageAdapter<>(mBannerViews));
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void addNewsData(List<DailyNews.StoriesBean> news) {
        for (DailyNews.StoriesBean bean : news) {
            bean.date = new Date(System.currentTimeMillis());
        }
        mNews.addAll(news);
        mRv.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLastItemVisible() {
        mPresenter.loadData(ZhiHuDailyContract.AFTER_DAY_NEWS);
    }

}

