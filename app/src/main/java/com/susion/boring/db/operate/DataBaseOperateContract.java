package com.susion.boring.db.operate;

import com.susion.boring.db.model.SimpleSong;

import java.util.List;

import rx.Observable;

/**
 * Created by susion on 17/2/20.
 */
public interface DataBaseOperateContract {

    interface BaseOperate<T>{
        Observable<List<T>> add(final List<T> songs);
        Observable<Boolean> add(T song);
        Observable<Boolean> delete(T song);
        Observable<Boolean> clearALLData();
        Observable<Boolean> query(String id);
        Observable<Long> getTotalCount();
        Observable<List<T>> getAll();

    }

}
