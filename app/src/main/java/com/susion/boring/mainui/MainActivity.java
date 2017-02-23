package com.susion.boring.mainui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import com.susion.boring.base.BaseActivity;
import com.susion.boring.base.DrawerData;
import com.susion.boring.mainui.drawer.MainDrawerAdapter;
import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.StatusBarUtil;
import com.susion.boring.utils.UIUtils;
import com.susion.boring.view.SToolBar;

public class MainActivity extends BaseActivity{

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerMenu;
    private RecyclerView mDrawerList;
    private ViewPager mViewPager;

    public static void start(Context srcContext){
        Intent intent = new Intent();
        intent.setClass(srcContext, MainActivity.class);
        srcContext.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected void setStatusBar() {
//        int mStatusBarColor = getResources().getColor(R.color.colorPrimaryDark);
//        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), mStatusBarColor, 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void findView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerMenu = (LinearLayout) findViewById(R.id.drawer);
        mDrawerList = (RecyclerView) findViewById(R.id.list_view);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    public void initView() {
        mToolBar.setLeftIcon(R.mipmap.top_menu);
        mDrawerList.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));
        mDrawerList.addItemDecoration(RVUtils.getDrawerItemDecorationDivider(this, R.color.divider, new Rect(UIUtils.dp2Px(40), UIUtils.dp2Px(40), UIUtils.dp2Px(30), 0), DrawerData.getData()));

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.getMainUIFragments().get(position);
            }
            @Override
            public int getCount() {
                return FragmentFactory.getMainUIFragments().size();
            }
        });
    }

    @Override
    public void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mToolBar.setSelectedItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mToolBar.setItemClickListener(new SToolBar.OnItemClickListener() {
            @Override
            public void onMenuItemClick(View v) {
                mDrawerLayout.openDrawer(mDrawerMenu);
            }
            @Override
            public void onMusicItemClick(View v) {
                showCurrentSelectedFragment();
            }

            @Override
            public void onPlayerItemClick(View v) {
                showCurrentSelectedFragment();
            }

            @Override
            public void onInterestingItemClick(View v) {
                showCurrentSelectedFragment();
            }
        });

        mDrawerList.setAdapter(new MainDrawerAdapter(this, DrawerData.getData()));
        showCurrentSelectedFragment();
    }

    @Override
    public void initData() {

    }

    private void showCurrentSelectedFragment() {
        mViewPager.setCurrentItem(mToolBar.getCurrentSelectItem());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(MusicInstruction.SERVICE_SAVE_LAST_PLAY_MUSIC);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
