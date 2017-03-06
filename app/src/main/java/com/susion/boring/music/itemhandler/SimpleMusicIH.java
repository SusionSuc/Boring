package com.susion.boring.music.itemhandler;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;


/**
 * Created by susion on 17/2/15.
 */
public abstract class SimpleMusicIH<T> extends SimpleItemHandler<T> {

    protected SimpleDraweeView mSdvAlbum;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mSdvAlbum = vh.get(R.id.item_local_music_iv_album_pic);
    }

    @Override
    public void onBindDataView(ViewHolder vh, final  T data, int position) {
        bindData(vh, data, position);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_local_music;
    }

    @Override
    public void onClick(View view) {
        onClickEvent();
    }

    protected abstract void onClickEvent();
    protected abstract void bindData(ViewHolder vh, final  T data, int position);
}
