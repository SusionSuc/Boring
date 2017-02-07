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
import com.susion.boring.http.APIHelper;
import com.susion.boring.music.adapter.MusicSearchResultAdapter;
import com.susion.boring.music.model.MusicSearchResult;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.SystemOperationUtils;
import com.susion.boring.view.SearchBar;

import rx.Observer;

public class SearchMusicActivity extends Activity {

    private SearchBar mSearchBar;
    private RecyclerView mRV;
    private View mHolderView;
    private MusicSearchResultAdapter mAdapter;
    private ImageView mTvHolderImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_music);

        findView();
        initView();

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

        mSearchBar.setSearchButtonClickListener(new SearchBar.OnSearchButtonClickListener() {
            @Override
            public void doSearch(String searchContent) {

                mSearchBar.disableSearchBt();
                showWaitAnimation();

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
                                mSearchBar.enableSearchBt();
                                mTvHolderImageView.clearAnimation();
                                SystemOperationUtils.closeSystemKeyBoard(SearchMusicActivity.this);

                                if (musicSearchResult.code != APIHelper.REQUEST_SUCCESSS) {
                                    return;
                                }

                                if (mAdapter == null) {
                                    initRvData(musicSearchResult);
                                } else {
                                    setData(musicSearchResult);
                                }
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

    private void initRvData(MusicSearchResult data) {
        mHolderView.setVisibility(View.INVISIBLE);
        mRV.setVisibility(View.VISIBLE);
        mAdapter = new MusicSearchResultAdapter(this, data.result.songs);
        mRV.setAdapter(mAdapter);
        mRV.addItemDecoration(RVUtils.getItemDecorationDivider(this, R.color.divider, 1));

        mRV.getAdapter().notifyItemChanged(1);
    }

    public void setData(MusicSearchResult data) {
        mRV.setVisibility(View.VISIBLE);
        mHolderView.setVisibility(View.INVISIBLE);

        mAdapter.refreshData(data.result.songs);
    }
}
