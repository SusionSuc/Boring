package com.susion.boring.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susion.boring.R;
import com.susion.boring.base.BaseFragment;
import com.susion.boring.base.OnLastItemVisibleListener;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.base.view.LoadMoreView;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.http.APIHelper;
import com.susion.boring.music.adapter.MusicPageAdapter;
import com.susion.boring.music.itemhandler.MusicPageConstantIH;
import com.susion.boring.music.model.GetPlayListResult;
import com.susion.boring.music.model.MusicPageConstantItem;
import com.susion.boring.music.model.SimpleTitle;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.ClientReceiverPresenter;
import com.susion.boring.music.presenter.command.ClientPlayControlCommand;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.music.view.MusicControlPanel;
import com.susion.boring.utils.BroadcastUtils;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.SPUtils;
import com.susion.boring.view.SearchBar;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/1/19.
 */
public class MusicPageFragment extends BaseFragment implements OnLastItemVisibleListener, MediaPlayerContract.BaseView{

    private SearchBar mSearchBar;
    private LoadMoreRecycleView mRV;
    private MusicControlPanel mControlView;

    private final int PLAY_LIST_PAGE_SIZE = 6;
    private List<Object> mData = new ArrayList<>();
    private int page = 0;
    private Song mSong;
    private MediaPlayerContract.ClientPlayControlCommand mControlCommand;
    private MediaPlayerContract.ClientReceiverPresenter mClientReceiver;


    @Override
    public View initContentView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_music_page_layout, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mControlCommand = new ClientPlayControlCommand(getActivity());
        mClientReceiver = new ClientReceiverPresenter(getActivity());
        mClientReceiver.setBaseView(this);

        mSearchBar = (SearchBar) mView.findViewById(R.id.search_bar);
        mSearchBar.setJumpToSearchPage(true);
        mSearchBar.setBackground(R.color.colorLightPrimary);

        mRV = (LoadMoreRecycleView) mView.findViewById(R.id.list_view);
        mRV.setLayoutManager(RVUtils.getStaggeredGridLayoutManager(2));
        mRV.setOnLastItemVisibleListener(this);

        initConstantItem();
        MusicPageAdapter mAdapter = new MusicPageAdapter(getActivity(), mData);
        mRV.setAdapter(mAdapter);

        mControlView = (MusicControlPanel) mView.findViewById(R.id.music_control_view);

    }

    @Override
    public void initListener() {
        mControlView.seMusicControlListener(new MusicControlPanel.MusicControlViewListener() {
            @Override
            public void onPlayClick(boolean isPlay) {
                if (isPlay) {
                    BroadcastUtils.sendIntentAction(getActivity(), MusicInstruction.SERVICE_RECEIVER_PLAY_MUSIC);
                } else {
                    BroadcastUtils.sendIntentAction(getActivity(), MusicInstruction.SERVICE_RECEIVER_PAUSE_MUSIC);
                }
            }

            @Override
            public void onNextClick() {
                mControlView.setPlay(false);
                mControlView.setMusic(mSong);
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
        mControlCommand.queryServiceIsPlaying();
        mControlCommand.getCurrentPlayMusic();
    }

    private void loadMusicRecommendList() {
        mRV.setLoadStatus(LoadMoreView.LOADING);
        APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getPlayList(page * PLAY_LIST_PAGE_SIZE, PLAY_LIST_PAGE_SIZE), new Observer<GetPlayListResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(GetPlayListResult playLists) {
                mRV.setLoadStatus(LoadMoreView.NO_LOAD);
                mData.addAll(playLists.getPlaylists());
                page++;
                mRV.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void loadPLayHistory() {
        String songId = SPUtils.getStringFromMusicConfig(SPUtils.MUSIC_CONFIG_LAST_PLAY_MUSIC, getActivity());
        DbBaseOperate<SimpleSong> dbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), getContext(), SimpleSong.class);
        dbOperator.query(songId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SimpleSong>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

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
        mData.add(new MusicPageConstantItem(R.mipmap.icon_local_music, "本地音乐", MusicPageConstantIH.LOCAL_MUSIC));
        mData.add(new MusicPageConstantItem(R.mipmap.icon_my_collect, "我的喜欢", MusicPageConstantIH.MY_COLLECT));
        mData.add(new SimpleTitle());
    }


    @Override
    public void onLastItemVisible() {
        loadMusicRecommendList();
    }



    //implements CommunicateBaseView
    @Override
    public void tryToChangeMusicByCurrentCondition(boolean playStatus, boolean needLoadMusic) {
        mControlView.setPlay(playStatus);
        if (!playStatus && needLoadMusic) {
            mControlCommand.loadMusicInfoToService(mSong, false);
        }
    }

    @Override
    public void refreshSong(Song song) {
        mControlView.setVisibility(View.VISIBLE);
        mSong = song;
        mControlView.setMusic(song);
    }

    @Override
    public Context getContext(){
        return getActivity();
    }


}
