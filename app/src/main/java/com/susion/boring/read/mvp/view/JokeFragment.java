package com.susion.boring.read.mvp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.base.ui.OnLastItemVisibleListener;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.base.view.ViewPageFragment;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.http.APIHelper;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.read.itemhandler.JokeIH;
import com.susion.boring.read.mvp.entity.Joke;
import com.susion.boring.read.mvp.entity.JokeList;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by susion on 17/3/15.
 */
public class JokeFragment extends ViewPageFragment implements OnLastItemVisibleListener, SwipeRefreshLayout.OnRefreshListener {

    private LoadMoreRecycleView mRv;
    private List<Joke> mData = new ArrayList<>();
    private Set<String> mIds = new HashSet<>();
    private int mPage = 1;
    private SwipeRefreshLayout mRefreshLayout;

    private boolean mIsRefreshing;
    private TextView mTvUpdateTip;
    private DbBaseOperate<Joke> mDbOperator;


    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_joke_layout, container, false);
        return mView;
    }

    @Override
    protected void findView() {
        mDbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), getActivity(), Joke.class);

        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refresh_layout);
        mRv = (LoadMoreRecycleView) mView.findViewById(R.id.list_view);
        mTvUpdateTip = (TextView) mView.findViewById(R.id.tv_update_tip);
    }

    @Override
    protected void initView() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(new int[]{getResources().getColor(R.color.colorAccent)});
        mRv.setLayoutManager(RVUtils.getLayoutManager(getContext(), LinearLayoutManager.VERTICAL));
        mRv.addItemDecoration(new RVUtils.NoLastDividerDecoration(getContext(), R.color.divider, 1, new Rect(0, 0, 0, 0)));
        mRv.setOnLastItemVisibleListener(this);
        mRv.setAdapter(new BaseRVAdapter(getActivity(), mData) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new JokeIH(mDbOperator);
                    }
                });
            }

            @Override
            protected int getViewType(int position) {
                return 0;
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Log.e("JokeFragment", "initData");
        loadData();
    }

    private void loadData() {
        String timestampStr = String.valueOf(new Date().getTime()).substring(0, 10);

        APIHelper.subscribeSimpleRequest(APIHelper.getJokeService().getJokes(mPage, 10, timestampStr), new CommonObserver<JokeList>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mIsRefreshing = false;
                mRefreshLayout.setRefreshing(mIsRefreshing);
            }

            @Override
            public void onNext(JokeList jokeList) {
                List<Joke> list = jokeList.getResult();
                if (jokeList != null && list != null) {
                    if (!mIsRefreshing) {
                        for (Joke joke : list) {
                            if (mIds.add(joke.getHashId())) {
                                mData.add(joke);
                            }
                        }
                        mRv.getAdapter().notifyDataSetChanged();
                        mPage++;

                    } else {
                        mIsRefreshing = false;
                        mRefreshLayout.setRefreshing(mIsRefreshing);

                        int a = 0;
                        for (Joke joke : list) {
                            if (mIds.add(joke.getHashId())) {
                                mData.add(0, joke);
                                a++;
                            }
                        }
                        showUpdateTip(a + " 条新笑话");
                        mRv.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    mIsRefreshing = false;
                    mRefreshLayout.setRefreshing(mIsRefreshing);
                }
            }
        });
    }

    @Override
    public String getTitle() {
        return "热门段子";
    }

    @Override
    public void onLastItemVisible() {
        loadData();
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        loadData();
        mIsRefreshing = true;
    }

    protected void showUpdateTip(String tip) {
        if (mTvUpdateTip == null) {
            return;
        }
        mTvUpdateTip.setTranslationY(-1.0f * UIUtils.dp2Px(getContext().getResources().getDimension(R.dimen.update_tip_height)));
        mTvUpdateTip.setVisibility(View.VISIBLE);
        mTvUpdateTip.setText(tip);
        mTvUpdateTip.animate().setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTvUpdateTip.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideTipText();
                    }
                }, 500);
            }
        }).translationY(0.0f);
    }

    private void hideTipText() {
        if (mTvUpdateTip == null) {
            return;
        }
        mTvUpdateTip.animate()
                .translationY(0)
                .translationY(-1.0f * UIUtils.dp2Px(getContext().getResources().getDimension(R.dimen.update_tip_height)))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mTvUpdateTip.setVisibility(View.GONE);
                    }
                });
    }
}
