package com.susion.boring.music.mvp.presenter;

import com.susion.boring.base.entity.ModelTranslateContract;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.music.mvp.model.SimpleSong;
import com.susion.boring.http.APIHelper;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.model.PlayListDetail;
import com.susion.boring.music.mvp.model.PlayListSong;
import com.susion.boring.music.mvp.model.Song;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by susion on 17/3/3.
 */
public class MusicModelTranslatePresenter implements ModelTranslateContract.MusicModeTranslate {

    @Override
    public Observable<List<Song>> getSongFromPlayList(final PlayList playList) {
        return Observable.create(new Observable.OnSubscribe<List<Song>>() {
            @Override
            public void call(final Subscriber<? super List<Song>> subscriber) {
                APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getPlayListDetail(Integer.valueOf(playList.getId())), new CommonObserver<PlayListDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(PlayListDetail playListDetail) {
                        subscriber.onNext(translateTracksToSong(playListDetail.getPlaylist().getTracks()));
                    }
                });
            }
        });
    }

    @Override
    public List<SimpleSong> translateTracksToSimpleSong(List<PlayListDetail.PlaylistBean.TracksBean> tracks) {
        List<SimpleSong> datas = new ArrayList<>();
        for (PlayListDetail.PlaylistBean.TracksBean track : tracks) {
            datas.add(track.translateToSimpleSong());
        }
        return datas;
    }

    @Override
    public List<Song> translateTracksToSong(List<PlayListDetail.PlaylistBean.TracksBean> tracks) {
        List<Song> datas = new ArrayList<>();
        for (PlayListDetail.PlaylistBean.TracksBean track : tracks) {
            Song song = track.translateToSimpleSong().translateToSong();
            song.fromPlayList = true;
            datas.add(song);
        }
        return datas;
    }

    @Override
    public Observable<Boolean> checkIfHasPlayUrl(final Song song) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getSongDetail(Integer.valueOf(song.id)), new CommonObserver<PlayListSong>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(PlayListSong songs) {
                        if (songs != null && !songs.getData().isEmpty()) {
                            song.audio = songs.getData().get(0).getUrl();
                            subscriber.onNext(true);
                        } else {
                            subscriber.onNext(false);
                        }
                    }
                });
            }
        });
    }

}
