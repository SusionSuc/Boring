package com.susion.boring.interesting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.BaseFragment;

/**
 * Created by susion on 17/1/19.
 */
public class InterestingPageFragment extends BaseFragment {
    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_interseting_page_layout, container, false);
        return mView;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
