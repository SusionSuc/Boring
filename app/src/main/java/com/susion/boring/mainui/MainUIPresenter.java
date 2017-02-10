package com.susion.boring.mainui;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/1/19.
 */
public class MainUIPresenter  implements  IMainUIPresenter{

    private IMainUIView mMainView;
    private List<Fragment> mFragments;

    public MainUIPresenter(IMainUIView mMainView) {
        this.mMainView = mMainView;
        mFragments = new ArrayList<>();
    }

    @Override
    public int getFragmentCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getPageFragmentByPos(int pos) {
        return mFragments.get(pos);
    }




}

