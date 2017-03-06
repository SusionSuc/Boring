package com.susion.boring.music.itemhandler;

import android.text.TextUtils;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.music.activity.PlayMusicActivity;
import com.susion.boring.utils.AlbumUtils;
import com.susion.boring.utils.TimeUtils;

/**
 * Created by susion on 17/3/6.
 */
public class LocalMusicIH extends SimpleMusicIH<SimpleSong> {

    @Override
    protected void onClickEvent() {
        if (!mData.isFromPlayList()) {
            PlayMusicActivity.start(mContext, mData.translateToSong(), false);
        } else {
            PlayMusicActivity.start(mContext, mData.translateToSong(), true);
        }
    }

    @Override
    protected void bindData(ViewHolder vh, SimpleSong data, int position) {
        vh.getTextView(R.id.item_local_music_tv_music_name).setText(data.getDisplayName() + "");
        String desc = data.getArtist();

        if (!TextUtils.isEmpty(data.getAlbum())) {
            desc += "-" + data.getAlbum();
        }

        vh.getTextView(R.id.item_local_music_tv_artist_album).setText(desc);

        if (data.isHasDown()) {
            AlbumUtils.setAlbum(mSdvAlbum, data.getPath());
            vh.getTextView(R.id.item_local_music_tv_duration).setText(TimeUtils.formatDuration(data.getDuration()));
        } else {
            mSdvAlbum.setImageURI(data.getPicPath());
            vh.getTextView(R.id.item_local_music_tv_duration).setVisibility(View.GONE);
        }
    }
}
