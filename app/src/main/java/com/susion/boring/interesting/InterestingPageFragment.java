package com.susion.boring.interesting;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseFragment;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.interesting.itemhandler.InterestingPageIH;
import com.susion.boring.interesting.model.InterestingColumnType;
import com.susion.boring.utils.RVUtils;

/**
 * Created by susion on 17/1/19.
 */
public class InterestingPageFragment extends BaseFragment {

    private LoadMoreRecycleView mRv;

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_interseting_page_layout, container, false);
        findView();
        initView();
        return mView;
    }

    private void findView() {
        mRv = (LoadMoreRecycleView)mView.findViewById(R.id.list_view);
    }

    private void initView() {
        mRv.setLayoutManager(RVUtils.getLayoutManager(getContext(), LinearLayoutManager.VERTICAL));
        mRv.setAdapter(new BaseRVAdapter(this, InterestingColumnType.getColumnS()) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new InterestingPageIH();
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
    }
}
