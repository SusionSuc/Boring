package com.susion.boring.music.presenter.command;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.db.DbManager;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.utils.ToastUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/2.
 */
public class ClientPlayCommand implements MediaPlayerContract.ClientCommand {

    protected Context mContext;

    public ClientPlayCommand(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void likeMusic(Song mSong) {
        updatePlayMusic(mSong);
        new DbBaseOperate<SimpleSong>(DbManager.getLiteOrm(), mContext, SimpleSong.class)
                .add(mSong.translateToSimpleSong())
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
                    public void onNext(Boolean b) {
                        if (b) {
                            ToastUtils.showShort("喜欢成功");
                        } else {
                            ToastUtils.showShort("喜欢失败");
                        }
                    }
                });
    }

    @Override
    public void updatePlayMusic(Song song) {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_UPDATE_PLAY_MUSIC_INFO);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_UPDATE_SONG, song);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void musicToNextPlay(Song mSong) {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_SONG_TO_NEXT_PLAY);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_SONG_TO_NEXT_PLAY, mSong);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

}
