package com.susion.boring.mainui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

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
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.StatusBarUtil;
import com.susion.boring.utils.UIUtils;
import com.susion.boring.view.SToolBar;

public class MainActivity extends BaseActivity implements IMainUIView{

    private DrawerLayout mDrawerLayout;
    private SToolBar mToolBar;
    private LinearLayout mDrawerMenu;
    private RecyclerView mDrawerList;
    private ViewPager mViewPager;


    public static void start(Context srcContext){
        Intent intent = new Intent();
        intent.setClass(srcContext, MainActivity.class);
        srcContext.startActivity(intent);
    }

    @Override
    protected void setStatusBar() {
        int mStatusBarColor = getResources().getColor(R.color.colorPrimaryDark);
        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), mStatusBarColor, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initViewAndListener();
        init();
    }


    private void findView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerMenu = (LinearLayout) findViewById(R.id.drawer);
        mToolBar = (SToolBar) findViewById(R.id.toolbar);
        mDrawerList = (RecyclerView) findViewById(R.id.list_view);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mToolBar.setLeftIcon(R.drawable.select_toolbar_menu);
        mDrawerList.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));
        mDrawerList.addItemDecoration(RVUtils.getItemDecorationDivider(this, R.color.white, UIUtils.dp2Px(10)));
    }

    private void initViewAndListener() {

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
    }

    private void init() {
        mDrawerList.setAdapter(new MainDrawerAdapter(this, DrawerData.getData()));
        showCurrentSelectedFragment();
    }

    private void showCurrentSelectedFragment() {
        mViewPager.setCurrentItem(mToolBar.getCurrentSelectItem());
    }

}
