package com.susion.boring.interesting.mvp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.view.ViewPageFragment;

/**
 * Created by susion on 17/3/15.
 */
public class PictureFragment extends ViewPageFragment{
    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_picture_layout, container, false);
        return mView;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public String getTitle() {
        return "图片精选";
    }
}
