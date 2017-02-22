package com.susion.boring.music.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.base.BaseRVAdapter;
import com.susion.boring.base.ItemHandler;
import com.susion.boring.base.ItemHandlerFactory;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.music.itemhandler.LocalMusicIH;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.LocalMusicContract;
import com.susion.boring.music.presenter.LocalMusicPresenter;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.ToastUtils;
import com.susion.boring.utils.UIUtils;
import com.susion.boring.view.SToolBar;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LocalMusicActivity extends BaseActivity implements LocalMusicContract.View{

    private LoadMoreRecycleView mRV;
    private List<SimpleSong> mData = new ArrayList<>();
    private ViewGroup mRefreshParent;
    private ImageView mRefreshView;
    private Button mBtScanStart;
    private LocalMusicContract.Presenter mPresenter;
    private DbBaseOperate<SimpleSong> mMusicDbOperator;

    public static void start(Activity context) {
        Intent intent = new Intent(context, LocalMusicActivity.class);
        context.startActivity(intent);
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
    }

    @Override
    public void initView() {
        mMusicDbOperator = new DbBaseOperate<>(DbManager.getLiteOrm(), this, SimpleSong.class);
        mPresenter = new LocalMusicPresenter(this, mMusicDbOperator);

        mToolBar.setTitle("本地音乐");
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
        mToolBar.setRightIcon(R.mipmap.scan_local_music);

        mRV.setLayoutManager(RVUtils.getLayoutManager(this, LinearLayoutManager.VERTICAL));
        mRV.addItemDecoration(RVUtils.getItemDecorationDivider(this, R.color.red_divider, 2, -1, UIUtils.dp2Px(70)));
        mRV.setAdapter(new BaseRVAdapter(this, mData) {
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
        mToolBar.setRightIconClickListener(new SToolBar.OnRightIconClickListener() {
            @Override
            public void onRightIconClick() {
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
        mMusicDbOperator.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<SimpleSong>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort("加载数据出错");
            }

            @Override
            public void onNext(List<SimpleSong> simpleSongs) {
                mData.addAll(simpleSongs);
                mRV.getAdapter().notifyDataSetChanged();
            }
        });
    }


    @Override
    public Context getContext() {
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

}
