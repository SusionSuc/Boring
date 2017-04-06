package com.susion.boring.read;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseFragment;
import com.susion.boring.base.view.ViewPageFragment;
import com.susion.boring.read.mvp.view.JokeFragment;
import com.susion.boring.read.mvp.view.NestChildViewPager;
import com.susion.boring.read.mvp.view.PictureFragment;
import com.susion.boring.read.mvp.view.ZhiHuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/1/19.
 */
public class ReadPageFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private NestChildViewPager mViewPager;
    private List<ViewPageFragment> mFragments;

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_interseting_page_layout, container, false);
        findView();
        initView();
        return mView;
    }

    @Override
    public void findView() {
        mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        mViewPager = (NestChildViewPager) mView.findViewById(R.id.view_pager);
    }

    private void initFragments() {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(new ZhiHuFragment());
            mFragments.add(new JokeFragment());
            mFragments.add(new PictureFragment());
        }
    }

    @Override
    public void initView() {
        initFragments();
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragments.get(position).getTitle();
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
    }
}
