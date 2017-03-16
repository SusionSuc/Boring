package com.susion.boring.interesting.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.adapter.QuickPageAdapter;
import com.susion.boring.base.mvp.model.TitleMark;
import com.susion.boring.base.ui.OnLastItemVisibleListener;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.base.view.ViewPageFragment;
import com.susion.boring.interesting.adapter.ZhiHuDailyAdapter;
import com.susion.boring.interesting.mvp.contract.ZhiHuDailyContract;
import com.susion.boring.interesting.mvp.model.DailyNews;
import com.susion.boring.interesting.mvp.presenter.ZhiHuDailyNewsPresenter;
import com.susion.boring.interesting.view.BannerView;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by susion on 17/3/15.
 */
public class ZhiHuFragment extends ViewPageFragment implements ZhiHuDailyContract.View, OnLastItemVisibleListener {
    private int mMaxScrollSize;
    private ViewPager mViewPager;
    private LoadMoreRecycleView mRv;
    private List<BannerView> mBannerViews;
    private List<TitleMark> mData = new ArrayList<>();

    private ZhiHuDailyContract.Presenter mPresenter;
    private TextView mTvTitle;
    private LinearLayoutManager mManager;

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_zhi_hu_layout, container, false);
        findView();
        initView();
        return mView;
    }

    public void findView() {
        mPresenter = new ZhiHuDailyNewsPresenter(this);
        mPresenter.setCurrentDate(new Date(System.currentTimeMillis()));
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mRv = (LoadMoreRecycleView) mView.findViewById(R.id.list_view);
        mTvTitle = (TextView) mView.findViewById(R.id.textview);
    }

    public void initView() {
        mTvTitle.setText("今日新闻");

        mManager = RVUtils.getLayoutManager(getContext(), LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(mManager);
        mRv.setAdapter(new ZhiHuDailyAdapter((Activity) getContext(), mData));
        mRv.setOnLastItemVisibleListener(this);
        mRv.addItemDecoration(RVUtils.getZhiHuDailyNewsDecoration(getContext(), UIUtils.dp2Px(20), new ZhiHuDailyContract.DailyNewsStickHeader() {

            @Override
            public String getTitle(int position) {
                if (position >= mData.size() || position == 0) {
                    return "";
                }
                return mData.get(position).getHeaderTitle();
            }

            @Override
            public int getHeaderColor(int position) {
                if (position == 0) {
                    return getResources().getColor(R.color.transparent);
                }
                return getResources().getColor(R.color.colorAccent);
            }


            @Override
            public boolean isShowTitle(int position) {
                if (position >= mData.size()) {
                    return false;
                }
                return mData.get(position).isShowTitle();
            }
        }));
        mRv.addItemDecoration(RVUtils.getItemDecorationDivider(getContext(), R.color.red_divider, 1, -1, UIUtils.dp2Px(15)));

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pos = mManager.findFirstVisibleItemPosition();
                if (pos >= 1 && pos < mData.size()) {
                    TitleMark preTitle = mData.get(pos - 1);
                    TitleMark title = mData.get(pos);

                    if (!preTitle.equals(title)) {
                        mTvTitle.setText(title.getHeaderTitle());
                    }
                }
            }
        });
    }


    @Override
    public void initData() {
        mPresenter.loadData(ZhiHuDailyContract.LATEST_NEWS);
    }

    @Override
    public void initListener() {

    }

    @Override
    public Context getViewContext() {
        return getContext();
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
        mData.addAll(news);
        mRv.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLastItemVisible() {
        mPresenter.loadData(ZhiHuDailyContract.AFTER_DAY_NEWS);
    }


    @Override
    public String getTitle() {
        return "知乎日报";
    }
}
