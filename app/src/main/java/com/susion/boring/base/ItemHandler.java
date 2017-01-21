package com.susion.boring.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by susion on 17/1/18.
 */
public interface ItemHandler<T extends Object>  {

    void  onCreateItemHandler(ViewHolder vh, ViewGroup parent);

    @LayoutRes
    int getLayoutResId();

    void onBindView(Object adapter, ViewHolder vh, T data, int position);

}
