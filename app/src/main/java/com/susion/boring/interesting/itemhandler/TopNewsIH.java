package com.susion.boring.interesting.itemhandler;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.interesting.mvp.model.DailyNews;

import java.util.List;

/**
 * Created by susion on 17/3/15.
 */
public class TopNewsIH  extends SimpleItemHandler<List<DailyNews.TopStoriesBean>>{

    @Override
    public void onBindDataView(ViewHolder vh, List<DailyNews.TopStoriesBean> data, int position) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_top_news;
    }

    @Override
    public void onClick(View v) {

    }



}
