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
    public void randomPlayMusic() {

    }

    @Override
    public void circlePlayMusic() {

    }

    @Override
    public void saveLastPlayMusic(Song song, Context c) {
        if (song != null) {
            SPUtils.writeStringToMusicConfig(SPUtils.MUSIC_CONFIG_LAST_PLAY_MUSIC, song.id, c);
        }
    }

    @Override
    public void nextPlay(Song song) {

    }

}
