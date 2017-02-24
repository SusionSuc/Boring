package com.susion.boring.db.operate;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.susion.boring.db.model.SimpleSong;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by susion on 17/2/23.
 */
public class MusicDbOperator extends DbBaseOperate<SimpleSong> implements DataBaseOperateContract.MusicOperator{

    public MusicDbOperator(LiteOrm mLiteOrm, Context mContext, Class c) {
        super(mLiteOrm, mContext, c);
    }


    @Override
    public Observable<List<SimpleSong>> getLikeMusic() {
        return Observable.create(new Observable.OnSubscribe<List<SimpleSong>>() {
            @Override
            public void call(Subscriber<? super List<SimpleSong>> subscriber) {
                List<SimpleSong> likes = mLiteOrm.query(new QueryBuilder<>(SimpleSong.class).whereEquals("favorite",true));
                subscriber.onNext(likes);
            }
        });
    }

    @Override
    public Observable<Long> getLikeMusicCount(){
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                long count = mLiteOrm.queryCount(new QueryBuilder(SimpleSong.class).whereEquals("favorite",true));
                subscriber.onNext(count);
            }
        });
    }


    @Override
    public Observable<List<SimpleSong>> getLocalMusic(){
        return Observable.create(new Observable.OnSubscribe<List<SimpleSong>>() {
            @Override
            public void call(Subscriber<? super List<SimpleSong>> subscriber) {
                List<SimpleSong> locals = mLiteOrm.query(new QueryBuilder<>(SimpleSong.class).whereEquals("hasDown",true));
                subscriber.onNext(locals);
            }
        });
    }

    @Override
    public Observable<Long> getLocalMusicCount(){
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                long count = mLiteOrm.queryCount(new QueryBuilder(SimpleSong.class).whereEquals("hasDown",true));
                subscriber.onNext(count);
            }
        });
    }




}
