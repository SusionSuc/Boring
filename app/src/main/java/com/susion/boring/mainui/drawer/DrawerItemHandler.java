package com.susion.boring.mainui.drawer;

import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.DrawerData;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;

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
        vh.getTextView(R.id.drawer_item_title).setText(data.item);

        if (data.imageRes != -1) {
            vh.getImageView(R.id.drawer_item_image).setImageResource(data.imageRes);
        }

    }

    @Override
    public int getLayoutResId() {
        return R.layout.drawer_item;
    }

    @Override
    public void onClick(View view) {

    }
}
