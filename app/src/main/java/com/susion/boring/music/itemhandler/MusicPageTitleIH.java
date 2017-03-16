package com.susion.boring.music.itemhandler;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.music.mvp.model.SimpleTitle;

/**
 * Created by susion on 17/2/23.
 */
public class MusicPageTitleIH extends SimpleItemHandler<SimpleTitle>{


    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        ViewGroup.LayoutParams layoutParams = vh.getConvertView().getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }

    @Override
    public void onBindDataView(ViewHolder vh, SimpleTitle data, int position) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_simple_title;
    }

    @Override
    public void onClick(View v) {

    }

}
