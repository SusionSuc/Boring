package com.susion.boring.music.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.base.BaseRVAdapter;
import com.susion.boring.base.ItemHandler;
import com.susion.boring.base.ItemHandlerFactory;
import com.susion.boring.base.MusicModelTranslatePresenter;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.http.APIHelper;
import com.susion.boring.music.itemhandler.LocalMusicIH;
import com.susion.boring.music.model.PlayList;
import com.susion.boring.music.model.PlayListDetail;
import com.susion.boring.music.presenter.command.ClientPlayModeCommand;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.music.view.PlayOperatorView;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.ToastUtils;
import com.susion.boring.view.SToolBar;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

public class PlayListActivity extends BaseActivity {

    private static final String PLAY_LIST = "PLAY_LIST";
    private SimpleDraweeView mSdvBg;
    private PlayList mPlayList;
    private LoadMoreRecycleView mRv;
    private List<SimpleSong> mData = new ArrayList<>();
    private SToolBar mToolBar2;
    private AppBarLayout mAppBarLayout;
    private int mMaxScrollSize;
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 90;


    private PlayOperatorView mPlayOperatorView;
    private MediaPlayerContract.ClientPlayModeCommand mPlayModeCommand;


    public static void start(Context mContext, PlayList mData) {
        Intent intent = new Intent();
        intent.putExtra(PLAY_LIST, mData);
        intent.setClass(mContext, PlayListActivity.class);
        mContext.startActivity(intent);
    }


    @Override
    protected void setStatusBar() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    @Override
    public int getLayoutId() {
        mPlayList = (PlayList) getIntent().getSerializableExtra(PLAY_LIST);
        return R.layout.activity_play_list;
    }

    @Override
    public void findView() {
        mPlayModeCommand = new ClientPlayModeCommand(this);

        mSdvBg = (SimpleDraweeView) findViewById(R.id.ac_play_list_iv_bg);
        mRv = (LoadMoreRecycleView) findViewById(R.id.list_view);
        mToolBar2 = (SToolBar) findViewById(R.id.ac_play_list_tool_bar2);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.ac_play_list_app_bar_layout);
        mPlayOperatorView = (PlayOperatorView) findViewById(R.id.play_operator);
    }

    @Override
    public void initView() {
        mToolBar.setTitle(mPlayList.getName());
        mToolBar.setBackgroundResource(R.color.transparent);
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
        mSdvBg.setImageURI(mPlayList.getCoverImgUrl());
        mToolBar2.setTitle("共 "+mPlayList.getTrackCount()+" 首");
        mToolBar2.setBackgroundResource(R.color.white);
        mToolBar2.setTitleColorRes(R.color.black);

        mRv.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));
        mRv.setAdapter(new BaseRVAdapter(this, mData) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new LocalMusicIH();
                    }
                });
            }

            @Override
            protected int getViewType(int position) {
                return 0;
            }
        });
    }

    @Override
    public void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (mMaxScrollSize == 0)
                    mMaxScrollSize = appBarLayout.getTotalScrollRange();

                int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                        / mMaxScrollSize;

                if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
                    mToolBar2.setBackgroundResource(R.color.transparent);
                    mToolBar2.setTitle(mPlayList.getName());
                    mToolBar2.setLeftIcon(R.mipmap.tool_bar_back_black);
                } else {
                    mToolBar2.setLeftIcon(SToolBar.HIDDEN_LEFT_ICON_RES);
                    mToolBar2.setBackgroundResource(R.color.white);
                    mToolBar2.setTitle("共 "+mPlayList.getTrackCount()+" 首");
                }

            }
        });

        mPlayOperatorView.setItemClickListener(new PlayOperatorView.OnItemClickListener() {
            @Override
            public void onCirclePlayItemClick() {
                ToastUtils.showShort("即将开始循环播放此歌单的音乐, 请稍等......");
                mPlayModeCommand.circlePlayPlayList(mPlayList);
            }

            @Override
            public void onRandomPlayItemClick() {

            }

            @Override
            public void onLikeItemClick() {

            }

            @Override
            public void onNextPlayItemClick() {

            }
        });
    }

    @Override
    public void initData() {
        loadData();
    }

    private void loadData() {
        APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getPlayListDetail(mPlayList.getId()), new Observer<PlayListDetail>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                int b = 0;
            }

            @Override
            public void onNext(PlayListDetail playListDetail) {
                mData.addAll(new MusicModelTranslatePresenter().translateTracksToSimpleSong(playListDetail.getPlaylist().getTracks()));
                mRv.getAdapter().notifyDataSetChanged();
            }
        });
    }

}
