package com.susion.boring.music.adapter;

import android.app.Activity;

import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.music.mvp.model.Song;

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
