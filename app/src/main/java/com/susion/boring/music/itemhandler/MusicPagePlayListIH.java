package com.susion.boring.music.itemhandler;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.music.mvp.view.PlayListActivity;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.utils.UIUtils;

/**
 * Created by susion on 17/2/23.
 */
public class MusicPagePlayListIH extends SimpleItemHandler<PlayList> {


    private SimpleDraweeView mSDVCover;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mSDVCover = vh.get(R.id.item_play_list_iv_cover);
    }

    @Override
    public void onBindDataView(ViewHolder vh, PlayList data, int position) {
        mSDVCover.setImageURI(data.getCoverImgUrl());
        vh.getTextView(R.id.item_play_list_tv_name).setText(data.getName());
        vh.getTextView(R.id.item_play_list_tv_play_count).setText(UIUtils.translatePlayCount(data.getPlayCount()));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_music_play_list;
    }

    @Override
    public void onClick(View v) {
        PlayListActivity.start(mContext, mData);
    }

}
