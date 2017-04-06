package com.susion.boring.read.itemhandler;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.read.mvp.entity.DailyNewsDate;

/**
 * Created by susion on 17/3/9.
 */
public class DailyNewsDateIH extends SimpleItemHandler<DailyNewsDate>{

    @Override
    public void onBindDataView(ViewHolder vh, DailyNewsDate data, int position) {
        vh.getTextView(R.id.item_daily_news_date_tv_date).setText(data.date+"");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_daily_news_date;
    }

    @Override
    public void onClick(View v) {
    }
}
