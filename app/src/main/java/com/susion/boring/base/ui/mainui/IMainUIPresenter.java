package com.susion.boring.base.ui.mainui;

import android.support.v4.app.Fragment;


/**
 * Created by susion on 17/1/19.
 */
public interface IMainUIPresenter {

    int getFragmentCount();

    Fragment getPageFragmentByPos(int pos);
}
