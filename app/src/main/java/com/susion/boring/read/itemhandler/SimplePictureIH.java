package com.susion.boring.read.itemhandler;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.susion.boring.R;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.event.CategoryPictureLoadErrorEvent;
import com.susion.boring.read.mvp.entity.SimplePicture;
import com.susion.boring.read.mvp.view.PictureViewActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by susion on 17/3/17.
 */
public class SimplePictureIH extends SimpleItemHandler<SimplePicture> {

    private SimpleDraweeView mSdvPicture;
    private boolean mLoadSuccess;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mSdvPicture = (SimpleDraweeView) vh.getImageView(R.id.item_simple_picture_sdv);
    }

    @Override
    public void onBindDataView(ViewHolder vh, SimplePicture data, int position) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new ImageControllerListener())
                .setUri(Uri.parse(data.getMiddle()))
                .build();
        mSdvPicture.setController(controller);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_simple_picture;
    }

    @Override
    public void onClick(View v) {
        if (!mLoadSuccess) {
            return;
        }

        int location[] = new int[2];
        mSdvPicture.getLocationOnScreen(location);
        PictureViewActivity.start((Activity) mContext, mData, new Rect(location[0], location[1], mSdvPicture.getWidth(), mSdvPicture.getHeight()));
    }

    private class ImageControllerListener extends BaseControllerListener<ImageInfo> {
        @Override
        public void onFinalImageSet(
                String id,
                @Nullable ImageInfo imageInfo,
                @Nullable Animatable anim) {
            if (imageInfo == null) {
                mLoadSuccess = false;
                return;
            }
            mLoadSuccess = true;
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            mLoadSuccess = false;
            EventBus.getDefault().post(new CategoryPictureLoadErrorEvent(""));
        }
    }
}
