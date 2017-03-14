package com.susion.boring.base.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.base.adapter.ViewHolder;

/**
 * Created by susion on 17/1/18.
 */
public abstract class SimpleItemHandler<T extends Object> implements ItemHandler<T>, View.OnClickListener {

    protected Context mContext;

    protected T mData;
    protected int mPosition;
    protected Object mAdapter;
    @Override

    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        if (mContext == null) {
            mContext = vh.getContext();
        }
        vh.getConvertView().setOnClickListener(this);
    }

    /**
     * 这里不建议有产生对象的操作(比如设置监听),把对象的生成放在 {@link #onCreateItemHandler(ViewHolder)} 方法里面吧.
     * @param vh
     * @param data
     * @param position
     */
    @Override
    final public void onBindView(Object adapter, ViewHolder vh, T data, int position) {
        mData=data;
        mPosition=position;
        mAdapter = adapter;
        onBindDataView(vh, data, position);
    }

    public abstract void onBindDataView(ViewHolder vh, T data, int position);
}
