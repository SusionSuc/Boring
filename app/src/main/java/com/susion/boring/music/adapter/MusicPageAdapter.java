package com.susion.boring.music.adapter;

import android.app.Activity;

import com.susion.boring.base.BaseRVAdapter;
import com.susion.boring.base.ItemHandler;
import com.susion.boring.base.ItemHandlerFactory;
import com.susion.boring.music.itemhandler.MusicPageConstantIH;
import com.susion.boring.music.model.MusicPageConstantItem;

import java.util.List;

/**
 * Created by susion on 17/1/21.
 */
public class MusicPageAdapter extends BaseRVAdapter{

    private static final int MUSIC_PAGE_CONSTANT_ITEM = 1;

    public MusicPageAdapter(Activity activity, List<?> data) {
        super(activity, data);
    }

    @Override
    protected void initHandlers() {
        registerItemHandler(MUSIC_PAGE_CONSTANT_ITEM, new ItemHandlerFactory() {
            @Override
            public ItemHandler newInstant(int viewType) {
                return new MusicPageConstantIH();
            }
        });
    }

    @Override
    protected int getViewType(int position) {

        Object o = mData.get(position);

        if (o instanceof MusicPageConstantItem) {
            return MUSIC_PAGE_CONSTANT_ITEM;
        }

        return 0;
    }
}
