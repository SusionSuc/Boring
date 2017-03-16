package com.susion.boring.base.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.base.view.ViewPageFragment;
import com.susion.boring.utils.RVUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/3/6.
 */
public abstract class ListFragment<T> extends ViewPageFragment{

    private LoadMoreRecycleView mRv;
    private List<T> mData = new ArrayList<>();

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_list, container, false);
        initView();
        return mView;
    }

    @Override
    public void initView() {
        mRv = (LoadMoreRecycleView) mView.findViewById(R.id.list_view);


        RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
        if (itemDecoration != null) {
             mRv.addItemDecoration(getItemDecoration());
        }

        mRv.setLayoutManager(RVUtils.getLayoutManager(getContext(), LinearLayoutManager.VERTICAL));

        mRv.setAdapter(new BaseRVAdapter(getActivity(), mData) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return getSingletonItemHandler();
                    }
                });
            }

            @Override
            protected int getViewType(int position) {
                return 0;
            }
        });

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        loadData();
    }

    public void addData(List<T> datas) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(datas);
        mRv.getAdapter().notifyDataSetChanged();
    }

    public LoadMoreRecycleView getRv() {
        return mRv;
    }


    // call add data method to refresh data
    protected abstract void loadData();

    protected abstract ItemHandler getSingletonItemHandler();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.ItemDecoration getItemDecoration();

}
