package com.susion.boring.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseFragment;
import com.susion.boring.base.ui.OnLastItemVisibleListener;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.base.view.LoadMoreView;
import com.susion.boring.db.DbManager;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.music.mvp.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.http.APIHelper;
import com.susion.boring.music.adapter.MusicPageAdapter;
import com.susion.boring.music.itemhandler.MusicPageConstantIH;
import com.susion.boring.music.mvp.model.GetPlayListResult;
import com.susion.boring.music.mvp.model.MusicPageConstantItem;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.model.SimpleTitle;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.music.mvp.presenter.ClientReceiverPresenter;
import com.susion.boring.music.service.action.ClientPlayControlCommand;
import com.susion.boring.music.mvp.contract.MediaPlayerContract;
import com.susion.boring.music.service.MusicServiceInstruction;
import com.susion.boring.music.view.MusicControlPanel;
import com.susion.boring.utils.BroadcastUtils;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.SPUtils;
import com.susion.boring.base.view.SearchBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/1/19.
 */
public class MusicPageFragment extends BaseFragment implements OnLastItemVisibleListener, MediaPlayerContract.BaseView {

    private SearchBar mSearchBar;
    private LoadMoreRecycleView mRV;
    private MusicControlPanel mControlView;

    private final int PLAY_LIST_PAGE_SIZE = 6;
    private List<Object> mData = new ArrayList<>();
    private Set<String> uniqueData = new HashSet<>();
    private int page = 0;
    private Song mSong;
    private MediaPlayerContract.ClientPlayControlCommand mClientControlCommand;
    private MediaPlayerContract.ClientReceiverPresenter mClientReceiver;

    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_music_page_layout, container, false);
        return mView;
    }

    @Override
    public void initView() {
        mSearchBar.setJumpToSearchPage(true);
        mSearchBar.setBackground(R.color.colorLightPrimary);

        mRV.setLayoutManager(RVUtils.getStaggeredGridLayoutManager(2));
        mRV.setOnLastItemVisibleListener(this);

        initConstantItem();
        MusicPageAdapter mAdapter = new MusicPageAdapter(getActivity(), mData);
        mRV.setAdapter(mAdapter);
    }

    @Override
    protected void findView() {
        mClientControlCommand = new ClientPlayControlCommand(getActivity());
        mClientReceiver = new ClientReceiverPresenter(getActivity());
        mClientReceiver.setBaseView(this);

        mSearchBar = (SearchBar) mView.findViewById(R.id.search_bar);
        mRV = (LoadMoreRecycleView) mView.findViewById(R.id.list_view);
        mControlView = (MusicControlPanel) mView.findViewById(R.id.music_control_view);
    }

    @Override
    public void initListener() {
        mControlView.seMusicControlListener(new MusicControlPanel.MusicControlViewListener() {
            @Override
            public void onPlayClick(boolean isPlay) {
                if (isPlay) {
                    mClientControlCommand.play();
                } else {
                    mClientControlCommand.pausePlay();
                }
            }

            @Override
            public void onNextClick() {
                mClientControlCommand.changeToNextMusic();
            }
        });
    }


    @Override
    public void initData() {
        loadPLayHistory();
        loadMusicRecommendList();
    }

    @Override
    public void onResume() {
        super.onResume();
        mClientControlCommand.getCurrentPlayMusic();
    }

    private void loadMusicRecommendList() {
        mRV.setLoadStatus(LoadMoreView.LOADING);
        APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getPlayList(page * PLAY_LIST_PAGE_SIZE, PLAY_LIST_PAGE_SIZE), new CommonObserver<GetPlayListResult>() {
            @Override
            public void onNext(GetPlayListResult playLists) {
                mRV.setLoadStatus(LoadMoreView.NO_LOAD);
                List<PlayList> playlists = playLists.getPlaylists();

                for (PlayList playList : playlists) {  //discard repeat data
                    if (uniqueData.add(playList.getId())) {
                        mData.add(playList);
                    }
                }
                page++;
                mRV.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void loadPLayHistory() {
        String songId = SPUtils.getStringFromConfig(SPUtils.KEY_LAST_PLAY_MUSIC);
        DbBaseOperate<SimpleSong> dbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), getContext(), SimpleSong.class);
        APIHelper.subscribeSimpleRequest(dbOperator.query(songId), new CommonObserver<SimpleSong>() {
            @Override
            public void onNext(SimpleSong song) {
                if (song != null) {
                    mControlView.setVisibility(View.VISIBLE);
                    mSong = song.translateToSong();
                    mControlView.setMusic(mSong);
                    mControlView.setPlay(false);
                } else {
                    mControlView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initConstantItem() {
        mData.add(new MusicPageConstantItem(R.mipmap.ic_local_music, "本地音乐", MusicPageConstantIH.LOCAL_MUSIC));
        mData.add(new MusicPageConstantItem(R.mipmap.ic_my_music_collect, "我的喜欢", MusicPageConstantIH.MY_COLLECT));
        mData.add(new SimpleTitle());
    }

    @Override
    public void onLastItemVisible() {
        loadMusicRecommendList();
    }

    @Override
    public void refreshSong(Song song, boolean playStatus) {
        mControlView.setVisibility(View.VISIBLE);
        mSong = song;
        mControlView.setMusic(song);
        mControlView.setPlay(playStatus);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

}
