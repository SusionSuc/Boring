package com.susion.boring.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by susion on 17/1/18.
 */
public abstract class BaseRVAdapter extends RecyclerView.Adapter{

    public static final String SPECIAL_FUNC_CAMERA = "SPECIAL_FUNC_CAMERA";

    protected List<?> mData;

    protected Activity mActivity;
    public SparseArray<ItemHandlerFactory> mItemHandlerHashMap = new SparseArray<>();


    public Object getItem(int position) {
        return mData != null && mData.size() > position && position >= 0 ? mData.get(position) : null;
    }

    protected abstract void initHandlers();

    public BaseRVAdapter() {
        initHandlers();
    }

    public BaseRVAdapter(Activity activity, List<?> data) {
        mData = data;
        mActivity = activity;
        initHandlers();

    }

    public BaseRVAdapter(Fragment fragment, List<?> data) {
        mData = data;
        mActivity = fragment.getActivity();
        initHandlers();
    }


    public void refreshData(List<?> data){
        mData = data;
        notifyDataSetChanged();
    }

    public void registerItemHandler(int viewType, ItemHandlerFactory itemHandlerFactory) {
        mItemHandlerHashMap.put(viewType, itemHandlerFactory);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    protected abstract int getViewType(int position);


    protected ItemHandler getItemHandler(int viewType) {
        return mItemHandlerHashMap.get(viewType).newInstant(viewType);
    }

    @Override
    public RcvAdapterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, getItemHandler(viewType));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ItemHandler itemHandler = ((ViewHolder) holder.itemView.getTag(R.id.item_tag_id)).itemHandler;

        if (itemHandler == null) {
            throw new RuntimeException(mData.get(position).getClass() + "  缺少ItemHandler 类,导致不能绑定数据");
        } else {
            if (mData != null) {
                itemHandler.onBindView(this, (ViewHolder) holder.itemView.getTag(R.id.item_tag_id), mData.get(position), position);
                ((ViewHolder) holder.itemView.getTag(R.id.item_tag_id)).position = position;
            }
        }
    }


    public static class RcvAdapterItem extends RecyclerView.ViewHolder {

        private ViewHolder vh;

        public RcvAdapterItem(Context context, ViewGroup parent, ItemHandler t) {
            super((LayoutInflater.from(context).inflate(t.getLayoutResId(), parent, false)));
            vh = ViewHolder.newInstant(itemView);
            vh.itemHandler = t;
            t.onCreateItemHandler(vh, parent);
        }
        public ViewHolder getInnerViewHolder() {
            return vh;
        }
    }
}
