package com.susion.boring.music.itemhandler;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.music.model.PlayList;

/**
 * Created by susion on 17/3/6.
 */
public class PLayListIH extends SimpleItemHandler<PlayList> {
    @Override
    public void onBindDataView(ViewHolder vh, PlayList data, int position) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_like_play_list;
    }

    @Override
    public void onClick(View v) {

    }
}
