package com.susion.boring.base.ui.mainui.drawer;

import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;

/**
 * Created by susion on 17/1/19.
 */
public class DrawerItemHandler extends SimpleItemHandler<DrawerData.DrawerItem> {

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
    }

    @Override

    public void onBindDataView(ViewHolder vh, DrawerData.DrawerItem data, int position) {
        vh.getTextView(R.id.drawer_item_title).setText(data.type);
        if (data.imageRes != -1) {
            vh.getImageView(R.id.drawer_item_image).setImageResource(data.imageRes);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_drawer_item;
    }

    @Override
    public void onClick(View view) {
        DrawerData.onItemClick(mContext, mData.type);
    }
}
