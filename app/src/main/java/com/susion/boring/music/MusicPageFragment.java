package com.susion.boring.music;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.BaseFragment;
import com.susion.boring.music.adapter.MusicPageAdapter;
import com.susion.boring.music.model.MusicPageConstantItem;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;
import com.susion.boring.view.SearchBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/1/19.
 */
public class MusicPageFragment extends BaseFragment {

    private SearchBar mSearchBar;
    private RecyclerView mRV;
    private List<Object> mData = new ArrayList<>();

    @Override
    public View initContentView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.fragment_music_page_layout, null);
        initView();
        return mView;
    }

    private void initView() {
        mSearchBar = (SearchBar) mView.findViewById(R.id.search_bar);
        mSearchBar.setJumpToSearchPage(true);
        mSearchBar.setBackground(R.color.colorLightPrimary);

        mRV = (RecyclerView) mView.findViewById(R.id.list_view);
        mRV.setLayoutManager(RVUtils.getLayoutManager(getActivity(), LinearLayoutManager.VERTICAL));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        initConstantItem();
        MusicPageAdapter mAdapter = new MusicPageAdapter(getActivity(), mData);
        mRV.setAdapter(mAdapter);
        mRV.addItemDecoration(RVUtils.getItemDecorationDivider(getActivity(), R.color.divider, 1, 2, UIUtils.dp2Px(60)));
    }

    private void initConstantItem() {
        mData.add(new MusicPageConstantItem(R.mipmap.icon_local_music, "本地音乐", "100"));
        mData.add(new MusicPageConstantItem(R.mipmap.icon_my_collect, "我的收藏", "100"));
    }

}
