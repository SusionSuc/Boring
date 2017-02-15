package com.susion.boring.music.itemhandler;

import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.utils.MusicLoader;

/**
 * Created by susion on 17/2/15.
 */
public class LocalMusicIH extends SimpleItemHandler<MusicLoader.MusicInfo> {


    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {

    }

    @Override
    public void onBindDataView(ViewHolder vh, MusicLoader.MusicInfo data, int position) {
        vh.getTextView(R.id.item_local_music_tv_music_name).setText(data.title+"");
        vh.getTextView(R.id.item_local_music_tv_artist_album).setText(data.artist+"-"+data.album);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_local_music;
    }


    @Override
    public void onClick(View view) {

    }
}
