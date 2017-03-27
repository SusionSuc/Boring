package com.susion.boring.db.operate;

import com.susion.boring.music.mvp.model.SimpleSong;

import java.util.List;

import rx.Observable;

/**
 * Created by susion on 17/2/20.
 */
public interface DataBaseOperateContract {

    interface BaseOperate<T> {
        Observable<List<T>> add(final List<T> ts);

        Observable<Boolean> add(T t);

        Observable<Boolean> delete(T t);

        Observable<Boolean> clearALLData();

        Observable<T> query(String id);

        Observable<Long> getTotalCount();

        Observable<List<T>> getAll();

        Observable<Boolean> update(T t);

        Observable<List<T>> getLikeData();
    }

    interface MusicOperator extends BaseOperate<SimpleSong> {
        Observable<List<SimpleSong>> getLikeMusic();

        Observable<Long> getLikeMusicCount();

        Observable<List<SimpleSong>> getLocalMusic();

        Observable<Long> getLocalMusicCount();
    }


}
