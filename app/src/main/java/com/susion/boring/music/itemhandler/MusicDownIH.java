package com.susion.boring.music.itemhandler;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.music.model.DownTask;

/**
 * Created by susion on 17/2/17.
 */
public class MusicDownIH extends SimpleItemHandler<DownTask> {


    @Override
    public void onBindDataView(ViewHolder vh, DownTask data, int position) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_music_down;
    }

    @Override
    public void onClick(View view) {

    }


}
