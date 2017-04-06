package com.susion.boring.read.itemhandler;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.adapter.QuickPageAdapter;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.read.mvp.entity.DailyNews;
import com.susion.boring.read.view.BannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/3/15.
 */
public class TopNewsIH extends SimpleItemHandler<List<DailyNews.TopStoriesBean>> {

    private ViewPager mViewPager;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mViewPager = vh.get(R.id.view_pager);
    }

    @Override
    public void onBindDataView(ViewHolder vh, List<DailyNews.TopStoriesBean> data, int position) {
        List<BannerView> mBannerViews = new ArrayList<>();
        for (DailyNews.TopStoriesBean bean : data) {
            BannerView view = new BannerView(mContext, bean);
            view.setTitle(bean.getTitle());
            view.setImgUrl(bean.getImage());
            mBannerViews.add(view);
        }
        mViewPager.setAdapter(new QuickPageAdapter<>(mBannerViews));
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_top_news;
    }

    @Override
    public void onClick(View v) {
    }
}
