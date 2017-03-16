package com.susion.boring.music.itemhandler;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.music.mvp.model.PlayList;

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
