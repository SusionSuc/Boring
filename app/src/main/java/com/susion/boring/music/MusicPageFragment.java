package com.susion.boring.music;

import android.view.LayoutInflater;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.BaseFragment;
import com.susion.boring.view.SearchBar;

/**
 * Created by susion on 17/1/19.
 */
public class MusicPageFragment extends BaseFragment {

    private SearchBar mSearchBar;

    @Override
    public View initContentView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.fragment_music_page_layout, null);
        mSearchBar = (SearchBar) mView.findViewById(R.id.search_bar);
        mSearchBar.setJumpToSearchPage(true);
        mSearchBar.setBackground(R.color.colorLightPrimary);
        return mView;
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {

    }

}
