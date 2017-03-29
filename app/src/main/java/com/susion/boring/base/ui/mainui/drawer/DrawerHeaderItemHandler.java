package com.susion.boring.base.ui.mainui.drawer;

import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.ui.SimpleItemHandler;
import com.susion.boring.base.adapter.ViewHolder;

/**
 * Created by susion on 17/1/18.
 */
public class DrawerHeaderItemHandler extends SimpleItemHandler<DrawerData.DrawerHeader> {

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
    }

    @Override
    public void onBindDataView(ViewHolder vh, DrawerData.DrawerHeader data, int position) {
        vh.getTextView(R.id.drawer_header_username).setText(data.username);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_drawer_header;
    }


    @Override
    public void onClick(View view) {

    }
}
