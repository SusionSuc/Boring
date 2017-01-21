package com.susion.boring.music.itemhandler;

import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.music.model.Song;

/**
 * Created by susion on 17/1/20.
 */
public class SearchMusicResultIH extends SimpleItemHandler<Song>{

    @Override
    public void onBindDataView(ViewHolder vh, Song data, int position) {
        vh.getTextView(R.id.item_music_search_tv_song_name).setText(data.name);
        vh.getTextView(R.id.item_music_search_tv_art_album).setText(data.artists.get(0).name+"-"+data.album.name);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_music_search_music_result;
    }

    @Override
    public void onClick(View view) {

    }
}
