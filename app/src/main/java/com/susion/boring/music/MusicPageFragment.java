package com.susion.boring.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.BaseFragment;
import com.susion.boring.music.activity.PlayMusicActivity;
import com.susion.boring.music.adapter.MusicPageAdapter;
import com.susion.boring.music.itemhandler.MusicPageConstantIH;
import com.susion.boring.music.model.MusicPageConstantItem;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.music.view.MusicControlView;
import com.susion.boring.utils.BroadcastUtils;
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
    private ClientMusicReceiver mReceiver;

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
            public void onPlayClick(boolean isPlay) {
                if (isPlay) {
                    BroadcastUtils.sendIntentAction(getActivity(), MusicInstruction.SERVICE_RECEIVER_PLAY_MUSIC);
                } else {
                    BroadcastUtils.sendIntentAction(getActivity(), MusicInstruction.SERVICE_RECEIVER_PAUSE_MUSIC);
                }
            }
            @Override
            public void onNextClick() {

            }
        });
    }

    @Override
    public void initData() {
        //init receiver and start service
        mReceiver = new ClientMusicReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, mReceiver.getIntentFilter());
        MusicInstruction.startMusicPlayService(getActivity());

        getCurrentPlayMusic();

        initConstantItem();
        MusicPageAdapter mAdapter = new MusicPageAdapter(getActivity(), mData);
        mRV.setAdapter(mAdapter);
        mRV.addItemDecoration(RVUtils.getItemDecorationDivider(getActivity(), R.color.divider, 1, 2, UIUtils.dp2Px(60)));
    }

    @Override
    public void onResume() {
        super.onResume();
        getCurrentPlayMusic();
    }

    private void getCurrentPlayMusic() {
        Intent intent = new Intent(MusicInstruction.SERVICE_CURRENT_PLAY_MUSIC);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

        //query play state
        BroadcastUtils.sendIntentAction(getActivity(), MusicInstruction.SERVICE_RECEIVER_QUERY_IS_PLAYING);  //查询当前的播放状态
    }

    private void initConstantItem() {
        mData.add(new MusicPageConstantItem(R.mipmap.icon_local_music, "本地音乐", MusicPageConstantIH.LOCAL_MUSIC));
        mData.add(new MusicPageConstantItem(R.mipmap.icon_my_collect, "我的收藏", MusicPageConstantIH.MY_COLLECT));
    }

    public class ClientMusicReceiver extends BroadcastReceiver {

        public ClientMusicReceiver() {
        }

        public IntentFilter getIntentFilter(){
            IntentFilter filter = new IntentFilter();
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_MUSIC);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_CURRENT_SERVER_STATE);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_CURRENT_IS_PALING);
            return filter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case MusicInstruction.CLIENT_RECEIVER_CURRENT_PLAY_MUSIC:
                    mSong = (Song) intent.getSerializableExtra(MusicInstruction.CLIENT_PARAM_CURRENT_PLAY_MUSIC);
                    if (mSong != null) {
                        mControlView.setPlay(false);
                        mControlView.setMusic(mSong);
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_CURRENT_IS_PALING:
                    mControlView.setPlay(intent.getBooleanExtra(MusicInstruction.CLIENT_PARAM_IS_PLAYING, false));
                    break;
            }
        }
    }

}
