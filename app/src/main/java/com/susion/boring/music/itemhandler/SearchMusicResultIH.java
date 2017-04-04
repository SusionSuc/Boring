package com.susion.boring.music.itemhandler;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.event.AddToNextPlayEvent;
import com.susion.boring.music.mvp.view.PlayMusicActivity;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.utils.ToastUtils;
import com.susion.boring.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by susion on 17/1/20.
 */
public class SearchMusicResultIH extends SimpleMusicIH<Song> {

    public SearchMusicResultIH(boolean showNextPlay) {
        super(showNextPlay);
    }

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mTvDuration.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onAddToNextPlayClick() {
        ToastUtils.showShort("已经添加下一首播放");
        EventBus.getDefault().post(new AddToNextPlayEvent(mData));
    }

    @Override
    protected void onItemClick() {
        PlayMusicActivity.start(mContext, mData, false);
    }

    @Override
    protected void bindData(ViewHolder vh, Song data, int position) {
        mTvTile.setText(data.name);
        if (!data.artists.isEmpty()) {
            mTvSecondTile.setText(data.artists.get(0).name + "-" + data.album.name);
        }
        UIUtils.loadSmallPicture(mSdvAlbum, data.album.picUrl);
    }

}
