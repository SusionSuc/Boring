package com.susion.boring.mainui.drawer;

import android.app.Activity;

import com.susion.boring.base.BaseRVAdapter;
import com.susion.boring.base.DrawerData;
import com.susion.boring.base.ItemHandler;
import com.susion.boring.base.ItemHandlerFactory;

import java.util.List;

/**
 * Created by susion on 17/1/18.
 */
public class MainDrawerAdapter extends BaseRVAdapter {

    private final int ITEM_HANDLER_HEADER = 1;
    private final int ITEM_HANDLER_ITEM = 2;

    public MainDrawerAdapter(Activity context, List<?> data) {
        super(context, data);
    }

    @Override
    protected void initHandlers() {
        registerItemHandler(ITEM_HANDLER_HEADER, new ItemHandlerFactory() {
            @Override
            public ItemHandler newInstant(int viewType) {
                return new DrawerHeaderItemHandler();
            }
        });

        registerItemHandler(ITEM_HANDLER_ITEM, new ItemHandlerFactory() {
            @Override
            public ItemHandler newInstant(int viewType) {
                return new DrawerItemHandler();
            }
        });

    }

    @Override
    protected int getViewType(int position) {
        Object o = mData.get(position);

        if (o instanceof DrawerData.DrawerHeader) {
            return ITEM_HANDLER_HEADER;
        }

        if (o instanceof DrawerData.DrawerItem) {
            return ITEM_HANDLER_ITEM;
        }

        return  -1;
    }

}
