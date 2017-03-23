package com.susion.boring.interesting.itemhandler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.interesting.mvp.model.SimplePicture;
import com.susion.boring.interesting.mvp.view.PictureViewActivity;

/**
 * Created by susion on 17/3/17.
 */
public class SimplePictureIH extends SimpleItemHandler<SimplePicture> {

    private SimpleDraweeView mSdvPicture;
    private Bitmap mBitmap;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        mSdvPicture = (SimpleDraweeView) vh.getImageView(R.id.item_simple_picture_sdv);
    }

    @Override
    public void onBindDataView(ViewHolder vh, SimplePicture data, int position) {
        mSdvPicture.setImageURI(data.getMiddle());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_simple_picture;
    }

    @Override
    public void onClick(View v) {
        int location[] = new int[2];
        mSdvPicture.getLocationOnScreen(location);
        PictureViewActivity.start((Activity) mContext, mData.getBig(), new Rect(location[0], location[1], mSdvPicture.getWidth(), mSdvPicture.getHeight()));
    }
}
