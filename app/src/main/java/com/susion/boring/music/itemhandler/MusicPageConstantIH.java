package com.susion.boring.music.itemhandler;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.music.activity.LocalMusicActivity;
import com.susion.boring.music.activity.MyMusicCollectActivity;
import com.susion.boring.music.model.MusicPageConstantItem;

/**
 * Created by susion on 17/1/21.
 */
public class MusicPageConstantIH extends SimpleItemHandler<MusicPageConstantItem> {

    public static final int LOCAL_MUSIC = 1;
    public static final int MY_COLLECT = 2;
    private MusicPageConstantItem mData;


    @Override
    public void onBindDataView(ViewHolder vh, MusicPageConstantItem data, int position) {
        mData = data;
        vh.getImageView(R.id.item_music_page_constant_iv_icon).setImageResource(data.iconId);
        vh.getTextView(R.id.item_music_page_constant_tv_item).setText(data.item);

        if (data.type == LOCAL_MUSIC) {
            vh.getTextView(R.id.item_music_page_constant_tv_append_dec).setText("加载本地音乐");
        }


        if (data.type  == MY_COLLECT) {
            vh.getTextView(R.id.item_music_page_constant_tv_append_dec).setText("加载我的收藏");
        }


    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_music_page_constant;
    }

    @Override
    public void onClick(View view) {

        if (mData.type == LOCAL_MUSIC) {
            LocalMusicActivity.start(mContext);
        }


        if (mData.type  == MY_COLLECT) {
            MyMusicCollectActivity.start(mContext);
        }
    }



}
