package com.susion.boring.mainui;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by susion on 17/1/19.
 */
public interface IMainUIPresenter {

    Fragment getPageFragment(int fragmentId);

    void showFragment(int fragmentId, int fragmentMusic, Activity context);
}
