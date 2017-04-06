package com.susion.boring.music.mvp.view;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.db.DbManager;
import com.susion.boring.event.AddToNextPlayEvent;
import com.susion.boring.http.APIHelper;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.music.mvp.contract.MediaPlayerContract;
import com.susion.boring.music.mvp.model.SimpleSong;
import com.susion.boring.db.operate.MusicDbOperator;
import com.susion.boring.music.itemhandler.LocalMusicIH;
import com.susion.boring.music.mvp.contract.LocalMusicContract;
import com.susion.boring.music.mvp.presenter.LocalMusicPresenter;
import com.susion.boring.music.service.action.ClientPlayQueueControlCommand;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.ToastUtils;
import com.susion.boring.utils.UIUtils;
import com.susion.boring.base.view.SToolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LocalMusicActivity extends BaseActivity implements LocalMusicContract.View {

    private LoadMoreRecycleView mRV;
    private List<SimpleSong> mData = new ArrayList<>();
    private ViewGroup mRefreshParent;
    private ImageView mRefreshView;
    private Button mBtScanStart;
    private LocalMusicContract.Presenter mPresenter;
    private MediaPlayerContract.ClientPlayQueueControlCommand mPlayQueueCommand;
    private MusicDbOperator mMusicDbOperator;
    private ImageView mIvNoLocalMusic;

    public static void start(Activity context) {
        Intent intent = new Intent(context, LocalMusicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initParamsAndPresenter() {
        super.initParamsAndPresenter();
        EventBus.getDefault().register(this);
        mMusicDbOperator = new MusicDbOperator(DbManager.getLiteOrm(), this, SimpleSong.class);
        mPresenter = new LocalMusicPresenter(this, mMusicDbOperator);
        mPlayQueueCommand = new ClientPlayQueueControlCommand(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_local_music;
    }

    @Override
    public void findView() {
        mRV = (LoadMoreRecycleView) findViewById(R.id.list_view);
        mRefreshParent = (ViewGroup) findViewById(R.id.refresh_parent);
        mRefreshView = (ImageView) findViewById(R.id.refresh);
        mBtScanStart = (Button) findViewById(R.id.local_music_bt_start_scan);
        mIvNoLocalMusic = (ImageView) findViewById(R.id.no_local_music);
    }

    @Override
    public void initView() {
        mToolBar.setTitle("本地音乐");
        mToolBar.setRightIcon(R.mipmap.ic_scan_local_music);

        mRV.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));
        mRV.setAdapter(new BaseRVAdapter(this, mData) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new LocalMusicIH(true);
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

        mIvNoLocalMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvNoLocalMusic.setVisibility(View.INVISIBLE);
                showScanLocalMusicUI();
            }
        });

        mToolBar.setRightIconClickListener(new SToolBar.OnRightIconClickListener() {
            @Override
            public void onRightIconClick() {
                mIvNoLocalMusic.setVisibility(View.INVISIBLE);
                showScanLocalMusicUI();
            }
        });

        mBtScanStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadLocalMusic();
            }
        });
    }

    @Override
    public void initData() {
        APIHelper.subscribeSimpleRequest(mMusicDbOperator.getLocalMusic(), new CommonObserver<List<SimpleSong>>() {
            @Override
            public void onNext(List<SimpleSong> simpleSongs) {
                if (simpleSongs == null || simpleSongs.isEmpty()) {
                    mIvNoLocalMusic.setVisibility(View.VISIBLE);
                    mRefreshView.setVisibility(View.INVISIBLE);
                    return;
                }

                mIvNoLocalMusic.setVisibility(View.INVISIBLE);
                mData.addAll(simpleSongs);
                mRV.getAdapter().notifyDataSetChanged();
            }
        });
    }


    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public LoaderManager getMyLoaderManager() {
        return getLoaderManager();
    }

    public void showScanLocalMusicUI() {
        mRV.setVisibility(View.INVISIBLE);
        mRefreshParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideScanLocalMusicUI() {
        mRV.setVisibility(View.VISIBLE);
        mRefreshParent.setVisibility(View.INVISIBLE);
        mRefreshView.clearAnimation();
    }

    @Override
    public void showScanResult(List<SimpleSong> songs) {
        if (songs.isEmpty()) {
            mIvNoLocalMusic.setVisibility(View.VISIBLE);
            return;
        }

        mData.clear();
        mData.addAll(songs);
        mRV.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showScanErrorUI() {
    }

    @Override
    public void startScanLocalMusic() {
        UIUtils.startSimpleRotateAnimation(mRefreshView);
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
