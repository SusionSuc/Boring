package com.susion.boring.music.mvp.presenter;

import com.susion.boring.db.DbManager;
import com.susion.boring.music.mvp.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.music.mvp.contract.PlayMusicPageContract;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/7.
 */
public class PlayMusicPagePresenter implements PlayMusicPageContract.Presenter {

    private PlayMusicPageContract.View mView;

    public PlayMusicPagePresenter(PlayMusicPageContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void doLikeOrDisLikeMusic(final Song song) {
        DbBaseOperate<SimpleSong> operate = new DbBaseOperate<>(DbManager.getLiteOrm(), mView.getViewContext(), SimpleSong.class);
        operate.add(song.translateToSimpleSong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean flag) {
                        mView.refreshLikeStatus(song.favorite);
                    }
                });
    }
}
