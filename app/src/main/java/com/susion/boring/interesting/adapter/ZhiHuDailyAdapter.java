package com.susion.boring.interesting.adapter;

import android.app.Activity;

import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.interesting.itemhandler.DailyNewsDateIH;
import com.susion.boring.interesting.itemhandler.DailyNewsIH;
import com.susion.boring.interesting.model.DailyNews;
import com.susion.boring.interesting.model.DailyNewsDate;

import java.util.List;

/**
 * Created by susion on 17/3/9.
 */
public class ZhiHuDailyAdapter extends BaseRVAdapter{

    private static final int TYPE_NEWS = 1;
    private static final int TYPE_DATE = 2;

    public ZhiHuDailyAdapter(Activity activity, List<?> data) {
        super(activity, data);
    }

    @Override
    protected void initHandlers() {
        registerItemHandler(TYPE_NEWS, new ItemHandlerFactory() {
            @Override
            public ItemHandler newInstant(int viewType) {
                return new DailyNewsIH();
            }
        });

        registerItemHandler(TYPE_DATE, new ItemHandlerFactory() {
            @Override
            public ItemHandler newInstant(int viewType) {
                return new DailyNewsDateIH();
            }
        });
    }

    @Override
    protected int getViewType(int position) {

        Object item = getItem(position);
        if (item instanceof DailyNews.StoriesBean) {
            return TYPE_NEWS;
        }

        if (item instanceof DailyNewsDate) {
            return TYPE_DATE;
        }

        return 0;
    }
}
