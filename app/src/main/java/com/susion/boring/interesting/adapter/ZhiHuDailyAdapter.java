package com.susion.boring.interesting.adapter;

import android.app.Activity;

import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.interesting.itemhandler.DailyNewsDateIH;
import com.susion.boring.interesting.itemhandler.DailyNewsIH;
import com.susion.boring.interesting.itemhandler.TopNewsIH;
import com.susion.boring.interesting.mvp.model.DailyNews;
import com.susion.boring.interesting.mvp.model.DailyNewsDate;

import java.util.List;

/**
 * Created by susion on 17/3/9.
 */
public class ZhiHuDailyAdapter extends BaseRVAdapter {

    private static final int TYPE_NEWS = 1;
    private static final int TYPE_DATE = 2;
    private static final int TYPE_TOP_NEWS = 3;

    public ZhiHuDailyAdapter(Activity activity, List<?> data) {
        super(activity, data);
    }

    @Override
    protected void initHandlers() {
        registerItemHandler(TYPE_TOP_NEWS, new ItemHandlerFactory() {
            @Override
            public ItemHandler newInstant(int viewType) {
                return new TopNewsIH();
            }
        });

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

        if (item instanceof List) {
            return TYPE_TOP_NEWS;
        }

        if (item instanceof DailyNews.StoriesBean) {
            return TYPE_NEWS;
        }

        if (item instanceof DailyNewsDate) {
            return TYPE_DATE;
        }

        return 0;
    }
}
