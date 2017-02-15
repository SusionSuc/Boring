package com.susion.boring.music.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.base.BaseRVAdapter;
import com.susion.boring.base.ItemHandler;
import com.susion.boring.base.ItemHandlerFactory;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.base.view.LoadMoreView;
import com.susion.boring.API.APIHelper;
import com.susion.boring.music.itemhandler.SearchMusicResultIH;
import com.susion.boring.music.model.MusicSearchResult;
import com.susion.boring.music.model.Song;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.SystemOperationUtils;
import com.susion.boring.view.SearchBar;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

public class SearchMusicActivity extends BaseActivity implements OnLastItemVisibleListener{

    private SearchBar mSearchBar;
    private LoadMoreRecycleView mRV;
    private View mHolderView;
    private ImageView mTvHolderImageView;

    private List<Song> mData = new ArrayList<>();
    private boolean mIsNewSearch;
    private int mPage = 0;
    private String mSearchContent;
    private int PAGE_SIZE = 20;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search_music;
    }

    @Override
    public void findView() {
        mSearchBar = (SearchBar) findViewById(R.id.search_bar);
        mRV = (LoadMoreRecycleView) findViewById(R.id.list_view);
        mHolderView = findViewById(R.id.place_holder_view);
        mTvHolderImageView = (ImageView) findViewById(R.id.ac_search_iv_holder_image);
    }

    @Override
    public void initView() {
        mSearchBar.setBackground(R.color.colorPrimary);
        mSearchBar.setJumpToSearchPage(false);
        mSearchBar.setSearchButtonVisible(View.VISIBLE);
        mRV.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));


        mRV.setAdapter(new BaseRVAdapter(this, mData) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new SearchMusicResultIH();
                    }
                });
            }
            @Override
            protected int getViewType(int position) {
                return 0;
            }
        });

        mRV.addItemDecoration(RVUtils.getItemDecorationDivider(this, R.color.divider, 1));
        mRV.setOnLastItemVisibleListener(this);
    }

    @Override
    public void initListener() {
        mSearchBar.setSearchButtonClickListener(new SearchBar.OnSearchButtonClickListener() {
            @Override
            public void doSearch(String searchContent) {
                mSearchBar.disableSearchBt();
                showWaitAnimation();
                mIsNewSearch = true;
                mSearchContent = searchContent;
                mPage = 0;
                loadData();
            }
        });
    }

    @Override
    public void initData() {

    }

    private void showWaitAnimation() {
        mHolderView.setVisibility(View.VISIBLE);
        mRV.setVisibility(View.INVISIBLE);
        RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(1000);
        mTvHolderImageView.startAnimation(animation);
    }

    @Override
    public void onLastItemVisible() {
        mPage++;
        mRV.setLoadStatus(LoadMoreView.LOADING);
        loadData();
    }

    private void loadData() {
        APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().searchMusic(mSearchContent, PAGE_SIZE, 1, mPage * PAGE_SIZE),
                new Observer<MusicSearchResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRV.setLoadStatus(LoadMoreView.LOAD_FAILED);
                    }

                    @Override
                    public void onNext(MusicSearchResult musicSearchResult) {

                        if (musicSearchResult.result.songCount <= 0) {
                            mRV.setLoadStatus(LoadMoreView.NO_DATA);
                        }

                        mRV.setLoadStatus(LoadMoreView.NO_LOAD);

                        if (musicSearchResult.code != APIHelper.REQUEST_SUCCESSS) {
                            return;
                        }

                        if (mIsNewSearch) {
                            mSearchBar.enableSearchBt();
                            mTvHolderImageView.clearAnimation();
                            mRV.setVisibility(View.VISIBLE);
                            mHolderView.setVisibility(View.INVISIBLE);
                            SystemOperationUtils.closeSystemKeyBoard(SearchMusicActivity.this);
                            mIsNewSearch = false;

                            mData.clear();
                            mData.addAll(musicSearchResult.result.songs);
                        } else {
                            mData.addAll(musicSearchResult.result.songs);
                        }

                        mRV.getAdapter().notifyDataSetChanged();
                    }
                }
        );
    }
}
