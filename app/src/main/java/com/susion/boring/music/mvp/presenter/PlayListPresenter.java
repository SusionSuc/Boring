package com.susion.boring.music.mvp.presenter;

import com.susion.boring.db.DbManager;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.http.APIHelper;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.model.PlayListDetail;
import com.susion.boring.music.mvp.contract.PlayListContract;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/6.
 */
public class PlayListPresenter implements PlayListContract.Presenter {

    private final DbBaseOperate<PlayList> mPlayDb;
    private PlayListContract.View mView;

    public PlayListPresenter(PlayListContract.View mView) {
        this.mView = mView;
        mPlayDb = new DbBaseOperate<>(DbManager.getLiteOrm(), mView.getViewContext(), PlayList.class);
    }

    @Override
    public void loadData(PlayList playList) {
        APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getPlayListDetail(Integer.valueOf(playList.getId())), new CommonObserver<PlayListDetail>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onNext(PlayListDetail playListDetail) {
                mView.addData(playListDetail);
            }
        });
    }

    @Override
    public void likePlayList(PlayList playList) {
        mPlayDb.add(playList)
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
                        mView.refreshPlayListLikeStatus(flag);
                    }
                });
    }

    @Override
    public void disLikePlayList(PlayList playList) {
        mPlayDb.delete(playList)
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
                        mView.refreshPlayListLikeStatus(!flag);
                    }
                });
    }

    @Override
    public void queryPlayListLikeStatus(PlayList playList) {
        mPlayDb.query(playList.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlayList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PlayList playList) {
                        if (playList != null) {
                            mView.refreshPlayListLikeStatus(true);
                        }
                    }
                });
    }
}
