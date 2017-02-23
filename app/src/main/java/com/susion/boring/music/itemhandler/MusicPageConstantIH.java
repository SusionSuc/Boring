package com.susion.boring.music.itemhandler;

import android.app.Activity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.db.DbManager;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.music.activity.LocalMusicActivity;
import com.susion.boring.music.activity.MusicDownLoadListActivity;
import com.susion.boring.music.activity.MyMusicCollectActivity;
import com.susion.boring.music.model.MusicPageConstantItem;
import com.susion.boring.music.presenter.FileDownloadPresenter;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/1/21.
 */
public class MusicPageConstantIH extends SimpleItemHandler<MusicPageConstantItem> {

    public static final int LOCAL_MUSIC = 1;
    public static final int MY_COLLECT = 2;
    public static final int DOWNLOAD_LIST = 3;
    private MusicPageConstantItem mData;
    private TextView tvItem;

    @Override
    public void onCreateItemHandler(ViewHolder vh, ViewGroup parent) {
        super.onCreateItemHandler(vh, parent);
        ViewGroup.LayoutParams layoutParams = vh.getConvertView().getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }

    @Override
    public void onBindDataView(final ViewHolder vh, MusicPageConstantItem data, int position) {
        mData = data;
        vh.getImageView(R.id.item_music_page_constant_iv_icon).setImageResource(data.iconId);
        tvItem = vh.getTextView(R.id.item_music_page_constant_tv_item);
        tvItem.setText(data.item);
        if (data.type == LOCAL_MUSIC) {
            vh.getTextView(R.id.item_music_page_constant_tv_append_dec).setText("-_-?");
            new DbBaseOperate<SimpleSong>(DbManager.getLiteOrm(), mContext, SimpleSong.class)
                    .getTotalCount()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            vh.getTextView(R.id.item_music_page_constant_tv_append_dec).setText("统计出错");
                        }

                        @Override
                        public void onNext(Long count) {
                            vh.getTextView(R.id.item_music_page_constant_tv_append_dec).setText(""+count);
                        }
                    });
        }


        if (data.type == MY_COLLECT) {
            vh.getTextView(R.id.item_music_page_constant_tv_append_dec).setText("15");
        }

        if (data.type == DOWNLOAD_LIST) {
            vh.getTextView(R.id.item_music_page_constant_tv_append_dec)
                    .setText(FileDownloadPresenter.getInstance().getTaskList().size()+"");
        }


    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_music_page_constant;
    }

    @Override
    public void onClick(View view) {
        switch (mData.type){
            case LOCAL_MUSIC:
                LocalMusicActivity.start((Activity) mContext);
                break;
            case MY_COLLECT:
                MyMusicCollectActivity.start(mContext);
                break;
            case DOWNLOAD_LIST:
                MusicDownLoadListActivity.start(mContext);
                break;
        }
    }


}
