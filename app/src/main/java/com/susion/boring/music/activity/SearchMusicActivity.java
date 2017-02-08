package com.susion.boring.music.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.BaseRVAdapter;
import com.susion.boring.base.ItemHandler;
import com.susion.boring.base.ItemHandlerFactory;
import com.susion.boring.http.APIHelper;
import com.susion.boring.music.adapter.MusicSearchResultAdapter;
import com.susion.boring.music.itemhandler.SearchMusicResultIH;
import com.susion.boring.music.model.MusicSearchResult;
import com.susion.boring.music.model.Song;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.SystemOperationUtils;
import com.susion.boring.view.SearchBar;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

public class SearchMusicActivity extends Activity {

    private SearchBar mSearchBar;
    private RecyclerView mRV;
    private View mHolderView;
    private ImageView mTvHolderImageView;
    private List<Song> mData = new ArrayList<>();
    private boolean mIsNewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_music);
        findView();
        initView();
        initListener();
    }

    private void findView() {
        mSearchBar = (SearchBar) findViewById(R.id.search_bar);
        mRV = (RecyclerView) findViewById(R.id.list_view);
        mHolderView = findViewById(R.id.place_holder_view);
        mTvHolderImageView = (ImageView) findViewById(R.id.ac_search_iv_holder_image);
    }

    private void initView() {
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
    }

    private void initListener() {
        mSearchBar.setSearchButtonClickListener(new SearchBar.OnSearchButtonClickListener() {
            @Override
            public void doSearch(String searchContent) {
                mSearchBar.disableSearchBt();
                showWaitAnimation();
                mIsNewSearch = true;

                APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().searchMusic(searchContent, 10, 1, 0),
                        new Observer<MusicSearchResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(MusicSearchResult musicSearchResult) {
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
        });
    }


    private void showWaitAnimation() {
        mHolderView.setVisibility(View.VISIBLE);
        mRV.setVisibility(View.INVISIBLE);
        RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(1000);
         mTvHolderImageView.startAnimation(animation);
    }


}
