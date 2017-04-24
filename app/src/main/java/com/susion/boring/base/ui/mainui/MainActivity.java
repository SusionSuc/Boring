package com.susion.boring.base.ui.mainui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.base.ui.mainui.drawer.DrawerData;
import com.susion.boring.base.ui.mainui.drawer.MainDrawerAdapter;
import com.susion.boring.music.MusicPageFragment;
import com.susion.boring.music.service.MusicServiceInstruction;
import com.susion.boring.read.ReadPageFragment;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;
import com.susion.boring.base.view.SToolBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerMenu;
    private RecyclerView mDrawerRv;
    private ViewPager mViewPager;

    private List<Fragment> mMainUIFragments = new ArrayList<>();

    public static void start(Context srcContext) {
        Intent intent = new Intent();
        intent.setClass(srcContext, MainActivity.class);
        srcContext.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mMainUIFragments.add(SToolBar.ITEM_MUSIC, new MusicPageFragment());
        mMainUIFragments.add(SToolBar.ITEM_INTERESTING, new ReadPageFragment());
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void findView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerMenu = (LinearLayout) findViewById(R.id.drawer);
        mDrawerRv = (RecyclerView) findViewById(R.id.list_view);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    public void initView() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mMainUIFragments.get(position);
            }

            @Override
            public int getCount() {
                return mMainUIFragments.size();
            }
        });

        mToolBar.setLeftIcon(R.mipmap.ic_menu);
        mToolBar.setMainPage(true, mViewPager);   //必须要先设置adapter
        mToolBar.showCurrentSelectedFragment();
        mDrawerRv.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));
        mDrawerRv.addItemDecoration(RVUtils.getDrawerItemDecorationDivider(this, R.color.divider, new Rect(UIUtils.dp2Px(40), UIUtils.dp2Px(40), UIUtils.dp2Px(30), 0), DrawerData.getData()));
    }

    @Override
    public void initListener() {
        mToolBar.setItemClickListener(new SToolBar.OnItemClickListener() {
            @Override
            public void onMenuItemClick(View v) {
                mDrawerLayout.openDrawer(mDrawerMenu);
            }
        });

        mDrawerRv.setAdapter(new MainDrawerAdapter(this, DrawerData.getData()));
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(MusicServiceInstruction.SERVER_RECEIVER_SAVE_LAST_PLAY_MUSIC);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
