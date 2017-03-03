package com.susion.boring.music.itemhandler;

import android.view.View;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.music.model.Song;

/**
 * Created by susion on 17/3/3.
 */
public class DialogMusicIH extends SimpleItemHandler<Song> {

    @Override
    public void onBindDataView(ViewHolder vh, Song data, int position) {
        TextView tvMusicName = vh.getTextView(R.id.item_dialog_music_tv_music_name);
        TextView tvArtist = vh.getTextView(R.id.item_dialog_music_tv_artist);
        if (data.isPlaying) {
            vh.getImageView(R.id.item_dialog_music_iv_volum).setVisibility(View.VISIBLE);
            tvMusicName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            tvArtist.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            vh.getImageView(R.id.item_dialog_music_iv_volum).setVisibility(View.GONE);
            tvMusicName.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryText));
            tvArtist.setTextColor(mContext.getResources().getColor(R.color.red_divider));
        }

        tvMusicName.setText(data.name+"");
        tvArtist.setText(" - "+data.getArtist());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_dialog_music_item;
    }

    @Override
    public void onClick(View v) {

    }

}
