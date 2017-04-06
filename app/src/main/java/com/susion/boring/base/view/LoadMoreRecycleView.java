package com.susion.boring.base.view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.base.ui.OnLastItemVisibleListener;
import com.susion.boring.utils.RVUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by susion on 17/1/19.
 */
public class LoadMoreRecycleView extends RecyclerView {

    Context mContext;
    private LoadMoreAdapter mLoadMoreAdapter;
    private boolean hasLastListener;

    public LoadMoreRecycleView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    private void init() {
        mContext = getContext();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof LoadMoreAdapter) {
            mLoadMoreAdapter = (LoadMoreAdapter) adapter;
            super.setAdapter(adapter);
        } else {
            mLoadMoreAdapter = new LoadMoreAdapter(adapter);
            super.setAdapter(mLoadMoreAdapter);
        }
    }

    @IntDef({LoadMoreView.LOADING, LoadMoreView.LOAD_FAILED, LoadMoreView.NO_LOAD, LoadMoreView.NO_DATA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreStatus {
    }

    public void setLoadStatus(@LoadMoreStatus int status) {
        if (mLoadMoreAdapter != null) {
            mLoadMoreAdapter.setLoadStatus(status);
        }
    }

    public void setOnLastItemVisibleListener(OnLastItemVisibleListener listener) {
        RVUtils.setOnLastItemVisibleListener(this, listener);
        hasLastListener = true;
    }


    public class LoadMoreAdapter extends Adapter<ViewHolder> {

        LoadMoreView mLoadMoreView;
        public Adapter mAdapter;
        public static final int LOAD_MORE_VIEW_TYPE = -100;

        public LoadMoreAdapter(Adapter adapter) {
            mAdapter = adapter;
        }

        private boolean isLoadMoreView(int position) {
            return hasLastListener ? position == mAdapter.getItemCount()
                    : position == 0 && mAdapter.getItemCount() == 0;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == LOAD_MORE_VIEW_TYPE) {
                mLoadMoreView = new LoadMoreView(mContext);
                return new LoadMoreVH(mLoadMoreView);
            }

            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!isLoadMoreView(position)) {
                mAdapter.onBindViewHolder(holder, position);
            } else {
                if (getLayoutManager() instanceof StaggeredGridLayoutManager
                        ) {
                    StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) (getLayoutManager()).generateDefaultLayoutParams();
                    layoutParams.setFullSpan(true);
                    holder.itemView.setLayoutParams(layoutParams);
                }

                if (holder instanceof VH) {
                    ((VH) holder).onBindViewHolder();
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (!isLoadMoreView(position)) {
                return mAdapter.getItemViewType(position);
            } else {
                return LOAD_MORE_VIEW_TYPE;
            }
        }

        @Override
        public int getItemCount() {

            if (hasLastListener) {
                return mAdapter.getItemCount() + 1;
            }

            return mAdapter.getItemCount();
        }

        public void setLoadStatus(int status) {
            if (mLoadMoreView != null) {
                mLoadMoreView.setLoadStatus(status);
            }
        }

        class LoadMoreVH extends VH {
            public LoadMoreVH(View itemView) {
                super(itemView);
            }
        }

        class VH extends ViewHolder {
            public VH(View itemView) {
                super(itemView);
            }

            public void onBindViewHolder() {
            }
        }

    }

}
