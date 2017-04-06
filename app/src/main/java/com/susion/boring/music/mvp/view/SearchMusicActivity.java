package com.susion.boring.music.mvp.view;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.SAppApplication;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.base.ui.OnLastItemVisibleListener;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.base.view.LoadMoreView;
import com.susion.boring.event.AddToNextPlayEvent;
import com.susion.boring.http.APIHelper;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.music.itemhandler.SearchMusicResultIH;
import com.susion.boring.music.mvp.contract.MediaPlayerContract;
import com.susion.boring.music.mvp.model.MusicSearchResult;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.music.service.action.ClientPlayQueueControlCommand;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.SystemOperationUtils;
import com.susion.boring.utils.UIUtils;
import com.susion.boring.base.view.SearchBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

public class SearchMusicActivity extends BaseActivity implements OnLastItemVisibleListener {

    private SearchBar mSearchBar;
    private LoadMoreRecycleView mRV;
    private View mHolderView;
    private ImageView mTvHolderImageView;

    private MediaPlayerContract.ClientPlayQueueControlCommand mPlayQueueCommand;
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
    protected void initParamsAndPresenter() {
        super.initParamsAndPresenter();
        EventBus.getDefault().register(this);
        mPlayQueueCommand = new ClientPlayQueueControlCommand(this);
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
                        return new SearchMusicResultIH(true);
                    }
                });
            }

            @Override
            protected int getViewType(int position) {
                return 0;
            }
        });

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
        UIUtils.startSimpleRotateAnimation(mTvHolderImageView);
    }

    @Override
    public void onLastItemVisible() {
        mPage++;
        mRV.setLoadStatus(LoadMoreView.LOADING);
        loadData();
    }

    private void loadData() {
        SystemOperationUtils.closeSystemKeyBoard(this);
        APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().searchMusic(mSearchContent, PAGE_SIZE, "search", mPage * PAGE_SIZE),
                new CommonObserver<MusicSearchResult>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mTvHolderImageView.clearAnimation();
                        mSearchBar.enableSearchBt();
                    }

                    @Override
                    public void onNext(MusicSearchResult musicSearchResult) {
                        if (musicSearchResult.result.songCount <= 0) {
                            mRV.setLoadStatus(LoadMoreView.NO_DATA);
                        }
                        mRV.setLoadStatus(LoadMoreView.NO_LOAD);

                        if (musicSearchResult.code != APIHelper.REQUEST_SUCCESS) {
                            return;
                        }

                        if (mIsNewSearch) {
                            mSearchBar.enableSearchBt();
                            mTvHolderImageView.clearAnimation();
                            mRV.setVisibility(View.VISIBLE);
                            mHolderView.setVisibility(View.INVISIBLE);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddToNextPlayEvent event) {
        mPlayQueueCommand.addMusicToNextPlay(event.song);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
