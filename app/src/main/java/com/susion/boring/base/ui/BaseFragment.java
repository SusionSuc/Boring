package com.susion.boring.base.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by susion on 17/1/19.
 */
public abstract class BaseFragment extends Fragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected View mView;

    private boolean mIsViewInit;
    private boolean mIsVisible;
    private boolean mIsDataInit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreShowStates(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.e(this.toString(), "onCreateView");
        if (mView == null) {
            mView = initContentView(inflater, container);
            findView();
            initView();
            initListener();
            mIsViewInit = true;
            tryToInitData();
        } else {
            if (mView.getParent() != null && mView.getParent() instanceof ViewGroup) {
                ((ViewGroup) (mView.getParent())).removeView(mView);
            }
        }
        return mView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e(this.toString(), "setUserVisibleHint :"+isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
        tryToInitData();
    }

    private boolean tryToInitData() {
        if (mIsVisible && mIsViewInit && !mIsDataInit ) {
            initData();
            mIsDataInit = true;
            return true;
        }
        return false;
    }

    public abstract View initContentView(LayoutInflater inflater, ViewGroup container);
    
    protected abstract void initView();

    protected abstract void findView();

    public abstract void initListener();

    public abstract void initData();

    //resolve fragment overlap issue
    private void restoreShowStates(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }
}
