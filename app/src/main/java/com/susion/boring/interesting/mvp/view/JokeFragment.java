package com.susion.boring.interesting.mvp.view;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.base.ui.OnLastItemVisibleListener;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.base.view.ViewPageFragment;
import com.susion.boring.http.APIHelper;
import com.susion.boring.interesting.itemhandler.JokeIH;
import com.susion.boring.interesting.mvp.model.Joke;
import com.susion.boring.interesting.mvp.model.JokeList;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/15.
 */
public class JokeFragment extends ViewPageFragment implements OnLastItemVisibleListener{

    private LoadMoreRecycleView mRv;
    private List<Joke> mData = new ArrayList<>();
    private int mPage = 1;

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_joke_layout, container, false);
        return mView;
    }

    @Override
    protected void findView() {
        mRv = (LoadMoreRecycleView) mView.findViewById(R.id.list_view);
        mRv.setLayoutManager(RVUtils.getLayoutManager(getContext(), LinearLayoutManager.VERTICAL));
        mRv.addItemDecoration(RVUtils.getItemDecorationDivider(getContext(), R.color.shallow_red_divider, UIUtils.dp2Px(10)));
        mRv.setOnLastItemVisibleListener(this);
        mRv.setAdapter(new BaseRVAdapter(getActivity(), mData) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new JokeIH();
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
    protected void initView() {

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
        APIHelper.getJokeService()
                .getJokes(mPage, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JokeList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int b = 0;
                    }

                    @Override
                    public void onNext(JokeList jokeList) {
                        if (jokeList != null && jokeList.getResult() != null) {
                            mData.addAll(jokeList.getResult());
                            mRv.getAdapter().notifyDataSetChanged();
                            mPage++;
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
}
