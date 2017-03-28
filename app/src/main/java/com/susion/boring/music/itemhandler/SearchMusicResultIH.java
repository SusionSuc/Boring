package com.susion.boring.music.itemhandler;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.music.mvp.view.PlayMusicActivity;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.utils.ToastUtils;

/**
 * Created by susion on 17/1/20.
 */
public class SearchMusicResultIH extends SimpleMusicIH<Song> {

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mTvDuration.setVisibility(View.INVISIBLE);
        mTvOperator.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onClickEvent() {
        if (TextUtils.isEmpty(mData.audio)) {
            ToastUtils.showShort("抱歉啦! 暂时没有播放资源");
        }
        PlayMusicActivity.start(mContext, mData, false);
    }

    @Override
    protected void bindData(ViewHolder vh, Song data, int position) {
        mTvTile.setText(data.name);
        if (!data.artists.isEmpty()) {
            mTvSecondTile.setText(data.artists.get(0).name + "-" + data.album.name);
        }
        int width = 50, height = 50;
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(data.album.picUrl))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(mSdvAlbum.getController())
                .setImageRequest(request)
                .build();
        mSdvAlbum.setController(controller);
    }

}
