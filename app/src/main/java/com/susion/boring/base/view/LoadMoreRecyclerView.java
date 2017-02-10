package com.susion.boring.base.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.susion.boring.utils.RVUtils;

/**
 * Created by susion on 17/2/10.
 */
public class LoadMoreRecyclerView extends LinearLayout {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private LoadMoreView mLoadMoreView;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //default vertical
    private void init() {
        mContext = getContext();
        setOrientation(LinearLayout.VERTICAL);
        mRecyclerView = new RecyclerView(mContext);
        LinearLayout.LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRecyclerView.setLayoutParams(params1);
        mRecyclerView.setLayoutManager(RVUtils.getLayoutManager(mContext, LinearLayoutManager.VERTICAL));
        addView(mRecyclerView);

        mLoadMoreView = new LoadMoreView(mContext);
        LinearLayout.LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoadMoreView.setLayoutParams(params2);
        addView(mLoadMoreView);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mRecyclerView.setAdapter(adapter);
    }

    public void setLoadStatus(int status){
        mLoadMoreView.setLoadStatus(status);
    }


    public void setLayoutManager(LinearLayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecorationDivider) {
        mRecyclerView.addItemDecoration(itemDecorationDivider);
    }

    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }
}
