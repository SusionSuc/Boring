package com.susion.boring.base.entity;

import com.susion.boring.music.mvp.model.SimpleSong;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.model.PlayListDetail;
import com.susion.boring.music.mvp.model.Song;

import java.util.List;

import rx.Observable;

/**
 * Created by susion on 17/3/3.
 */
public interface ModelTranslateContract {

    interface MusicModeTranslate {
        Observable<List<Song>> getSongFromPlayList(PlayList playList);

        List<SimpleSong> translateTracksToSimpleSong(List<PlayListDetail.PlaylistBean.TracksBean> tracks);

        List<Song> translateTracksToSong(List<PlayListDetail.PlaylistBean.TracksBean> tracks);

        Observable<Boolean> checkIfHasPlayUrl(Song song);
    }
}
