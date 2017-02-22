package com.susion.boring.music.presenter;

import android.content.Context;

import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.music.model.DownTask;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.utils.SPUtils;
import com.susion.boring.utils.ToastUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/2/8.
 */
public class PlayMusicPresenter extends MediaPlayPresenter implements MediaPlayerContract.PlayMusicControlPresenter {

    private DbBaseOperate<SimpleSong> mDbOperator;

    public PlayMusicPresenter(MediaPlayerContract.BaseView mView) {
        super(mView);
    }

    public PlayMusicPresenter(MediaPlayerContract.BaseView mView, Context context) {
        super(mView, context);
    }
    public PlayMusicPresenter(MediaPlayerContract.BaseView mView, Context context, DbBaseOperate<SimpleSong> operator){
        super(mView, context);
        mDbOperator = operator;
    }

    @Override
    public void downMusic(Song song) {
        if (song.fromLocalMusic) {
            ToastUtils.showShort("歌曲已经下载!");
            return;
        }

        FileDownloadPresenter downManager = FileDownloadPresenter.getInstance();
        DownTask task = new DownTask(song.audio);

        if (downManager.isDowning(task)) {
            ToastUtils.showShort("当前任务正在下载!!");
            return;
        }

        task.taskName = song.name + song.audio.substring(song.audio.lastIndexOf("."));
        downManager.addDownTask(task);
    }


    @Override
    public void randomPlayMusic() {

    }

    @Override
    public void circlePlayMusic() {

    }

    @Override
    public void likeMusic(Song song) {
        song.favorite = !song.favorite;
        mDbOperator.update(song.translateToSimpleSong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("喜欢失败");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        ToastUtils.showShort("喜欢成功");
                    }
                });
    }


    @Override
    public void saveLastPlayMusic(Song song, Context c) {
        SPUtils.writeStringToMusicConfig(SPUtils.MUSIC_CONFIG_LAST_PLAY_MUSIC, song.id, c);
    }

}
