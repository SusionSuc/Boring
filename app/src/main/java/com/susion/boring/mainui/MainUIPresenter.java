package com.susion.boring.mainui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.susion.boring.interesting.InterestingPageFragment;
import com.susion.boring.music.MusicPageFragment;
import com.susion.boring.player.PlayerPageFragment;
import com.susion.boring.view.SToolBar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by susion on 17/1/19.
 */
public class MainUIPresenter  implements  IMainUIPresenter{

    private IMainUIView mMainView;
    private Map<Integer, Fragment> mFragments;

    public MainUIPresenter(IMainUIView mMainView) {
        this.mMainView = mMainView;
        mFragments = new HashMap<>();
    }


    @Override
    public Fragment getPageFragment(int fragmentId) {
        switch (fragmentId) {
            case SToolBar.ITEM_MUSIC :
                return new MusicPageFragment();

            case SToolBar.ITEM_PLAYER :
                return new PlayerPageFragment();

            case SToolBar.ITEM_INTERESTING :
                return new InterestingPageFragment();
        }

        return null;
    }

    @Override
    public void showFragment(int layoutId, int fragmentId, Activity context) {
        FragmentManager fm = context.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment = mFragments.get(fragmentId);

        if (fragment == null) {
            fragment = getPageFragment(fragmentId);

            if (fragment == null) {
                return;
            }

            mFragments.put(fragmentId, fragment);
        }

        ft.replace(layoutId, fragment);
        ft.commit();
    }


}

