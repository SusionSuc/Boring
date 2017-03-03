package com.susion.boring.music.itemhandler;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.music.activity.PlayMusicActivity;
import com.susion.boring.utils.AlbumUtils;
import com.susion.boring.utils.TimeUtils;
import com.susion.boring.utils.ToastUtils;

import java.io.File;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by susion on 17/2/15.
 */
public class LocalMusicIH extends SimpleItemHandler<SimpleSong> {

    private SimpleDraweeView mSdvAlbum;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mSdvAlbum = vh.get(R.id.item_local_music_iv_album_pic);
    }

    @Override
    public void onBindDataView(ViewHolder vh, final  SimpleSong data, int position) {
        vh.getTextView(R.id.item_local_music_tv_music_name).setText(data.getDisplayName()+"");

        String desc = data.getArtist();

        if (!TextUtils.isEmpty(data.getAlbum())) {
            desc += "-"+data.getAlbum();
        }

        vh.getTextView(R.id.item_local_music_tv_artist_album).setText(desc);

        if (data.isHasDown()) {
            AlbumUtils.setAlbum(mSdvAlbum, data.getPath());
            vh.getTextView(R.id.item_local_music_tv_duration).setText(TimeUtils.formatDuration(data.getDuration()));
        } else {
            mSdvAlbum.setImageURI(data.getPicPath());
            vh.getTextView(R.id.item_local_music_tv_duration).setText("--");
        }

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_local_music;
    }

    @Override
    public void onClick(View view) {
        if (!mData.isFromPlayList()) {
            PlayMusicActivity.start(mContext, mData.translateToSong(), false);
        } else {
            PlayMusicActivity.start(mContext, mData.translateToSong(), true);
        }

    }
}
