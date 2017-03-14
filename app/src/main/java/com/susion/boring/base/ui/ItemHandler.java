package com.susion.boring.base.ui;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.susion.boring.base.adapter.ViewHolder;

/**
 * Created by susion on 17/1/18.
 */
public interface ItemHandler<T extends Object>  {

    void  onCreateItemHandler(ViewHolder vh, ViewGroup parent);

    @LayoutRes
    int getLayoutResId();

    void onBindView(Object adapter, ViewHolder vh, T data, int position);

}
