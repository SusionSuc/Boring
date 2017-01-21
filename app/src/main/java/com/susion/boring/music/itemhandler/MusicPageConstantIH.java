package com.susion.boring.music.itemhandler;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.music.model.MusicPageConstantItem;

/**
 * Created by susion on 17/1/21.
 */
public class MusicPageConstantIH extends SimpleItemHandler<MusicPageConstantItem> {


    @Override
    public void onBindDataView(ViewHolder vh, MusicPageConstantItem data, int position) {
        vh.getImageView(R.id.item_music_page_constant_iv_icon).setImageResource(data.iconId);
        vh.getTextView(R.id.item_music_page_constant_tv_item).setText(data.item);
        vh.getTextView(R.id.item_music_page_constant_tv_append_dec).setText(data.appendDesc);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_music_page_constant;
    }

    @Override
    public void onClick(View view) {

    }
}
