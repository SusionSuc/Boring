package com.susion.boring.music.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.base.BaseRVAdapter;
import com.susion.boring.base.ItemHandler;
import com.susion.boring.base.ItemHandlerFactory;
import com.susion.boring.base.view.LoadMoreRecycleView;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.db.operate.MusicDbOperator;
import com.susion.boring.music.itemhandler.LocalMusicIH;
import com.susion.boring.utils.RVUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyMusicCollectActivity extends BaseActivity {


    private LoadMoreRecycleView mRv;
    private List<SimpleSong> mData = new ArrayList<>();
    private MusicDbOperator mDbOperator;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyMusicCollectActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_music_collect;
    }

    @Override
    public void findView() {
        mRv = (LoadMoreRecycleView) findViewById(R.id.list_view);
    }

    @Override
    public void initView() {
        mToolBar.setTitle("我的喜欢");
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);

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

    }

    @Override
    public void initData() {
        mDbOperator = new MusicDbOperator(DbManager.getLiteOrm(), this, SimpleSong.class);
        loadData();
    }

    private void loadData() {
        mDbOperator.getLikeMusic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SimpleSong>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<SimpleSong> simpleSongs) {
                        if (simpleSongs != null) {
                            mData.addAll(simpleSongs);
                            mRv.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

}
