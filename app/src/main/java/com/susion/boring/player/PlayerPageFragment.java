package com.susion.boring.player;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.BaseFragment;

/**
 * Created by susion on 17/1/19.
 */
public class PlayerPageFragment extends BaseFragment {
    @Override
    public View initContentView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.fragment_player_page_layout, null);
        return mView;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}

