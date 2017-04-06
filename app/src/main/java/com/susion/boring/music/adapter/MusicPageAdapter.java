package com.susion.boring.music.adapter;

import android.app.Activity;

import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.music.itemhandler.MusicPageConstantIH;
import com.susion.boring.music.itemhandler.MusicPagePlayListIH;
import com.susion.boring.music.itemhandler.MusicPageTitleIH;
import com.susion.boring.music.mvp.model.MusicPageConstantItem;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.model.SimpleTitle;

import java.util.List;

/**
 * Created by susion on 17/1/21.
 */
public class MusicPageAdapter extends BaseRVAdapter {

    private static final int MUSIC_PAGE_CONSTANT_ITEM = 1;
    private static final int MUSIC_PAGE_TITLE_ITEM = 2;
    private static final int MUSIC_PAGE_PLAY_LIST_ITEM = 3;


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

        registerItemHandler(MUSIC_PAGE_TITLE_ITEM, new ItemHandlerFactory() {
            @Override
            public ItemHandler newInstant(int viewType) {
                return new MusicPageTitleIH();
            }
        });

        registerItemHandler(MUSIC_PAGE_PLAY_LIST_ITEM, new ItemHandlerFactory() {
            @Override
            public ItemHandler newInstant(int viewType) {
                return new MusicPagePlayListIH();
            }
        });

    }

    @Override
    protected int getViewType(int position) {

        Object o = mData.get(position);

        if (o instanceof MusicPageConstantItem) {
            return MUSIC_PAGE_CONSTANT_ITEM;
        }

        if (o instanceof SimpleTitle) {
            return MUSIC_PAGE_TITLE_ITEM;
        }

        if (o instanceof PlayList) {
            return MUSIC_PAGE_PLAY_LIST_ITEM;
        }

        return 0;
    }
}
