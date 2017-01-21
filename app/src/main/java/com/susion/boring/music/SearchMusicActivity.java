package com.susion.boring.music;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.http.APIHelper;
import com.susion.boring.music.adapter.MusicSearchResultAdapter;
import com.susion.boring.music.model.MusicSearchResult;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.view.SearchBar;

import rx.Observer;

public class SearchMusicActivity extends Activity {

    private SearchBar mSearchBar;
    private RecyclerView mRV;
    private View mHolderView;

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
    }

    private void initView() {
        mSearchBar.setBackground(R.color.colorPrimary);
        mSearchBar.setJumpToSearchPage(false);
        mSearchBar.setSearchButtonVisible(View.VISIBLE);
        mRV.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));

        mSearchBar.setSearchButtonClickListener(new SearchBar.OnSearchButtonClickListener() {
            @Override
            public void doSearch(String searchContent) {
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
                                setData(musicSearchResult);
                            }
                        }
                );
            }
        });


    }


    public void setData(MusicSearchResult data) {
        mHolderView.setVisibility(View.INVISIBLE);
        mRV.setVisibility(View.VISIBLE);

        mRV.setAdapter(new MusicSearchResultAdapter(this, data.result.songs));
        mRV.addItemDecoration(RVUtils.getItemDecorationDivider(this, R.color.divider, 1));

    }
}
