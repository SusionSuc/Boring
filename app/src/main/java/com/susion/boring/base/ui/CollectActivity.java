package com.susion.boring.base.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.susion.boring.R;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.event.EssayDeleteFromLikeEvent;
import com.susion.boring.event.JokeDeleteFormLikeEvent;
import com.susion.boring.event.PictureDeleteFormLikeEvent;
import com.susion.boring.http.APIHelper;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.read.itemhandler.DailyNewsIH;
import com.susion.boring.read.itemhandler.JokeIH;
import com.susion.boring.read.itemhandler.SimplePictureIH;
import com.susion.boring.read.mvp.entity.Joke;
import com.susion.boring.read.mvp.entity.NewsDetail;
import com.susion.boring.read.mvp.entity.SimplePicture;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

//知乎文章、 笑话、 图片
public class CollectActivity extends BaseActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<ListFragment> mFragments = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, CollectActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    public void findView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    @Override
    public void initView() {
        mToolBar.setTitle("我的喜欢");
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
        mFragments.add(new ZhiHuLikeFragment());
        mFragments.add(new JokeLikeFragment());
        mFragments.add(new PictureLikeFragment());
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    public static class ZhiHuLikeFragment extends ListFragment {
        private DbBaseOperate<NewsDetail> mDbOperator;

        @Override
        protected void loadData() {
            mDbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), getActivity(), NewsDetail.class);
            APIHelper.subscribeSimpleRequest(mDbOperator.getAll(), new CommonObserver<List<NewsDetail>>() {
                @Override
                public void onNext(List<NewsDetail> loveNews) {
                    addData(loveNews);
                }
            });
        }

        @Override
        protected ItemHandler getSingletonItemHandler() {
            return new DailyNewsIH();
        }

        @Override
        protected RecyclerView.LayoutManager getLayoutManager() {
            return RVUtils.getLayoutManager(getActivity(), LinearLayoutManager.VERTICAL);
        }

        @Override
        protected RecyclerView.ItemDecoration getItemDecoration() {
            return new RVUtils.NoLastDividerDecoration(getActivity(), R.color.divider, 1, new Rect(UIUtils.dp2Px(15), 0, 0, 0));
        }

        @Override
        public String getTitle() {
            return "文章";
        }


        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onMessageEvent(EssayDeleteFromLikeEvent event) {
            List<NewsDetail> pics = mData;
            int i = 0;
            for (; i < pics.size(); i++) {
                if (pics.get(i).id.equals(event.newsDetail.id)) {
                    break;
                }
            }
            mData.remove(i);
            mRv.getAdapter().notifyDataSetChanged();
        }

        @Override
        protected void findView() {
            EventBus.getDefault().register(this);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            EventBus.getDefault().unregister(this);
        }
    }

    public static class JokeLikeFragment extends ListFragment {
        private DbBaseOperate<Joke> mDbOperator;

        @Override
        protected void loadData() {
            mDbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), getActivity(), Joke.class);
            APIHelper.subscribeSimpleRequest(mDbOperator.getAll(), new CommonObserver<List<Joke>>() {
                @Override
                public void onNext(List<Joke> jokes) {
                    addData(jokes);
                }
            });
        }

        @Override
        protected ItemHandler getSingletonItemHandler() {
            return new JokeIH(mDbOperator);
        }

        @Override
        protected RecyclerView.LayoutManager getLayoutManager() {
            return RVUtils.getLayoutManager(getActivity(), LinearLayoutManager.VERTICAL);
        }

        @Override
        protected RecyclerView.ItemDecoration getItemDecoration() {
            return new RVUtils.NoLastDividerDecoration(getActivity(), R.color.divider, 1, new Rect(UIUtils.dp2Px(15), 0, 0, 0));
        }

        @Override
        public String getTitle() {
            return "笑话";
        }

        @Override
        protected void findView() {
            EventBus.getDefault().register(this);
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onMessageEvent(JokeDeleteFormLikeEvent event) {
            mData.remove(event.joke);
            mRv.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            EventBus.getDefault().unregister(this);
        }
    }

    public static class PictureLikeFragment extends ListFragment {
        private DbBaseOperate<SimplePicture> mDbOperator;

        @Override
        protected void loadData() {
            mDbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), getActivity(), SimplePicture.class);
            APIHelper.subscribeSimpleRequest(mDbOperator.getAll(), new CommonObserver<List<SimplePicture>>() {
                @Override
                public void onNext(List<SimplePicture> pictures) {
                    addData(pictures);
                }
            });
        }

        @Override
        protected ItemHandler getSingletonItemHandler() {
            return new SimplePictureIH();
        }

        @Override
        protected RecyclerView.LayoutManager getLayoutManager() {
            return RVUtils.getStaggeredGridLayoutManager(2);
        }

        @Override
        protected RecyclerView.ItemDecoration getItemDecoration() {
            return new RVUtils.NoLastDividerDecoration(getActivity(), R.color.divider, 1, new Rect(UIUtils.dp2Px(15), 0, 0, 0));

        }

        @Override
        public String getTitle() {
            return "图片";
        }

        @Override
        protected void findView() {
            EventBus.getDefault().register(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            mRv.getAdapter().notifyDataSetChanged();
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onMessageEvent(PictureDeleteFormLikeEvent event) {
            List<SimplePicture> pics = mData;
            int i = 0;
            for (; i < pics.size(); i++) {
                if (pics.get(i).id.equals(event.picture.id)) {
                    break;
                }
            }
            mData.remove(i);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            EventBus.getDefault().unregister(this);
        }
    }
}
