package com.susion.boring.music.itemhandler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.music.activity.PlayMusicActivity;
import com.susion.boring.utils.AlbumUtils;
import com.susion.boring.utils.ToastUtils;


/**
 * Created by susion on 17/2/15.
 */
public class LocalMusicIH extends SimpleItemHandler<SimpleSong> {


    private ImageView mIvAlbum;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mIvAlbum = vh.getImageView(R.id.item_local_music_iv_album_pic);
    }

    @Override
    public void onBindDataView(ViewHolder vh, SimpleSong data, int position) {
        vh.getTextView(R.id.item_local_music_tv_music_name).setText(data.getDisplayName()+"");
        vh.getTextView(R.id.item_local_music_tv_artist_album).setText(data.getArtist()+"-"+data.getAlbum());
        mIvAlbum.setImageBitmap(AlbumUtils.parseAlbum(data));

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_local_music;
    }

    @Override
    public void onClick(View view) {
        PlayMusicActivity.start(mContext, mData.translateToSong());
    }
}
