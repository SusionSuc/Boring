package com.susion.boring.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseFragment;

/**
 * Created by susion on 17/1/19.
 */
public class PlayerPageFragment extends BaseFragment {
    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_player_page_layout, container, false);
        return mView;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}

