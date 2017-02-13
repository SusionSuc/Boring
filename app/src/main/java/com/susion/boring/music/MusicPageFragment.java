package com.susion.boring.music;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.BaseFragment;
import com.susion.boring.music.adapter.MusicPageAdapter;
import com.susion.boring.music.itemhandler.MusicPageConstantIH;
import com.susion.boring.music.model.MusicPageConstantItem;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.view.MusicControlView;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.SPUtils;
import com.susion.boring.utils.UIUtils;
import com.susion.boring.view.SearchBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/1/19.
 */
public class MusicPageFragment extends BaseFragment {

    private List<Object> mData = new ArrayList<>();

    private SearchBar mSearchBar;
    private RecyclerView mRV;
    private MusicControlView mControlView;
    private Song mSong;

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

        mControlView = (MusicControlView)mView.findViewById(R.id.music_control_view);

    }

    @Override
    public void initListener() {
        mControlView.seMusicControlListener(new MusicControlView.MusicControlViewListener() {
            @Override
            public void onPlayClick() {

            }

            @Override
            public void onNextClick() {

            }
        });
    }

    @Override
    public void initData() {
        initConstantItem();
        MusicPageAdapter mAdapter = new MusicPageAdapter(getActivity(), mData);
        mRV.setAdapter(mAdapter);
        mRV.addItemDecoration(RVUtils.getItemDecorationDivider(getActivity(), R.color.divider, 1, 2, UIUtils.dp2Px(60)));

        mSong = SPUtils.getGson().fromJson(SPUtils.getStringFromMusicConfig(SPUtils.MUSIC_CONFIG_LAST_PLAY_MUSIC, getActivity()),
                Song.class);
        if (mSong != null) {
            mControlView.setPlay(false);
            mControlView.setAlbum(mSong.album.picUrl);
            mControlView.setSongInfo(mSong.artists.get(0).name, mSong.name);
        }
    }

    private void initConstantItem() {
        mData.add(new MusicPageConstantItem(R.mipmap.icon_local_music, "本地音乐", MusicPageConstantIH.LOCAL_MUSIC));
        mData.add(new MusicPageConstantItem(R.mipmap.icon_my_collect, "我的收藏", MusicPageConstantIH.MY_COLLECT));
    }

}
