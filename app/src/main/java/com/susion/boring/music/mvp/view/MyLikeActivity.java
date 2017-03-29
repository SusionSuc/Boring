package com.susion.boring.music.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ListFragment;
import com.susion.boring.base.adapter.ViewHolder;
import com.susion.boring.db.DbManager;
import com.susion.boring.music.mvp.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.db.operate.MusicDbOperator;
import com.susion.boring.music.itemhandler.LocalMusicIH;
import com.susion.boring.music.itemhandler.SimpleMusicIH;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.utils.RVUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyLikeActivity extends BaseActivity {
    private ViewPager mViewPager;
    private List<ListFragment> mFragments = new ArrayList<>();
    private TabLayout mTabLayout;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyLikeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_music_collect;
    }

    @Override
    public void findView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    @Override
    public void initView() {
        mToolBar.setTitle("我的喜欢");
        mToolBar.setLeftIcon(R.mipmap.ic_back);
        initMyFragment();

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

    private void initMyFragment() {
        mFragments.add(new LocalMusicFragment());
        mFragments.add(new PlayListFragment());
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    public static class LocalMusicFragment extends ListFragment<SimpleSong> {
        private MusicDbOperator mDbOperator;

        @Override
        protected void loadData() {
            mDbOperator = new MusicDbOperator(DbManager.getLiteOrm(), getContext(), SimpleSong.class);
            mDbOperator.getLikeMusic()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<SimpleSong>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<SimpleSong> simpleSongs) {
                            if (simpleSongs != null) {
                                addData(simpleSongs);
                            }
                        }
                    });
        }

        @Override
        protected ItemHandler getSingletonItemHandler() {
            return new LocalMusicIH();
        }

        @Override
        protected RecyclerView.LayoutManager getLayoutManager() {
            return RVUtils.getLayoutManager(getContext(), LinearLayoutManager.VERTICAL);
        }

        @Override
        protected RecyclerView.ItemDecoration getItemDecoration() {
            return null;
        }

        @Override
        public String getTitle() {
            return "音乐";
        }

        @Override
        protected void findView() {

        }

    }

    public static class PlayListFragment extends ListFragment<PlayList> {
        private DbBaseOperate<PlayList> mDbOperator;

        @Override
        protected void loadData() {
            mDbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), getContext(), PlayList.class);
            mDbOperator.getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<PlayList>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<PlayList> playLists) {
                            if (playLists != null) {
                                addData(playLists);
                            }
                        }
                    });
        }

        @Override
        protected ItemHandler getSingletonItemHandler() {
            return new SimpleMusicIH<PlayList>() {
                @Override
                protected void onClickEvent() {
                    PlayListActivity.start(getContext(), mData);
                }

                @Override
                protected void bindData(ViewHolder vh, PlayList data, int position) {
                    vh.getTextView(R.id.item_local_music_tv_music_name).setText(data.getName().trim());
                    vh.getTextView(R.id.item_local_music_tv_artist_album).setText(data.getPlayCount() + "");
                    vh.getTextView(R.id.item_local_music_tv_duration).setVisibility(View.GONE);
                    mSdvAlbum.setImageURI(data.getCoverImgUrl());
                }
            };
        }

        @Override
        protected RecyclerView.LayoutManager getLayoutManager() {
            return RVUtils.getLayoutManager(getContext(), LinearLayoutManager.VERTICAL);
        }

        @Override
        protected RecyclerView.ItemDecoration getItemDecoration() {
            return null;
        }

        @Override
        public String getTitle() {
            return "专辑";
        }

        @Override
        protected void findView() {

        }

    }
}