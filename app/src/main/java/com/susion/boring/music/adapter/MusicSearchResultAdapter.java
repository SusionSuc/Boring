package com.susion.boring.music.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.susion.boring.base.BaseRVAdapter;
import com.susion.boring.base.DrawerData;
import com.susion.boring.base.ItemHandler;
import com.susion.boring.base.ItemHandlerFactory;
import com.susion.boring.music.itemhandler.SearchMusicResultIH;
import com.susion.boring.music.model.Song;

import java.util.List;

/**
 * Created by susion on 17/1/20.
 */
public class MusicSearchResultAdapter extends BaseRVAdapter{

    private final int ITEM_MUSIC_SEARCH_RESULT = 1;

    public MusicSearchResultAdapter(Activity activity, List<?> data) {
        super(activity, data);
    }

    @Override
    protected void initHandlers() {
        registerItemHandler(ITEM_MUSIC_SEARCH_RESULT, new ItemHandlerFactory() {
            @Override
            public ItemHandler newInstant(int viewType) {
                return new SearchMusicResultIH();
            }
        });
    }

    @Override
    protected int getViewType(int position) {

        Object o = mData.get(position);

        if (o instanceof Song) {
            return ITEM_MUSIC_SEARCH_RESULT;
        }
        return 0;
    }
}
