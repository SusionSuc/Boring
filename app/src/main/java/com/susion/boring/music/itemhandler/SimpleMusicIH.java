package com.susion.boring.music.itemhandler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;


/**
 * Created by susion on 17/2/15.
 */
public abstract class SimpleMusicIH<T> extends SimpleItemHandler<T> {

    protected SimpleDraweeView mSdvAlbum;
    protected TextView mTvTile;
    protected TextView mTvSecondTile;
    protected TextView mTvDuration;
    protected ImageView mIvNextPlay;

    private boolean mShowNextPlay;

    public SimpleMusicIH(boolean showNextPlay) {
        this.mShowNextPlay = showNextPlay;
    }

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mSdvAlbum = vh.get(R.id.item_local_music_iv_album_pic);
        mTvSecondTile = vh.getTextView(R.id.item_local_music_tv_artist_album);
        mTvTile = vh.getTextView(R.id.item_local_music_tv_music_name);
        mTvDuration = vh.getTextView(R.id.item_local_music_tv_duration);
        mIvNextPlay = vh.getImageView(R.id.item_local_music_iv_add_to_next_play);

        if (!mShowNextPlay) {
            mIvNextPlay.setVisibility(View.INVISIBLE);
        } else {
            mIvNextPlay.setOnClickListener(this);
        }
    }

    @Override
    public void onBindDataView(ViewHolder vh, final T data, int position) {
        bindData(vh, data, position);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_local_music;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item_local_music_iv_add_to_next_play) {
            onAddToNextPlayClick();
            return;
        }

        onItemClick();
    }

    protected abstract void onAddToNextPlayClick();

    protected abstract void onItemClick();

    protected abstract void bindData(ViewHolder vh, final T data, int position);
}
