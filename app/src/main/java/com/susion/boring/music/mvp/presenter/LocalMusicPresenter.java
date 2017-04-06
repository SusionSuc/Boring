package com.susion.boring.music.mvp.presenter;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.susion.boring.music.mvp.model.SimpleSong;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.music.mvp.contract.LocalMusicContract;
import com.susion.boring.utils.FileUtils;
import com.susion.boring.utils.Md5Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/13/16
 * Time: 8:36 PM
 * Desc: LocalMusicPresenter
 */
public class LocalMusicPresenter implements LocalMusicContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "LocalMusicPresenter";

    private static final int URL_LOAD_LOCAL_MUSIC = 0;
    private static final Uri MEDIA_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final String WHERE = MediaStore.Audio.Media.IS_MUSIC + "=1 AND "
            + MediaStore.Audio.Media.SIZE + ">0";
    private static final String ORDER_BY = MediaStore.Audio.Media.DISPLAY_NAME + " ASC";
    private static String[] PROJECTIONS = {
            MediaStore.Audio.Media.DATA, // the real path
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.IS_RINGTONE,
            MediaStore.Audio.Media.IS_MUSIC,
            MediaStore.Audio.Media.IS_NOTIFICATION,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
    };

    private LocalMusicContract.View mView;
    private DbBaseOperate<SimpleSong> mDbOperator;

    public LocalMusicPresenter(LocalMusicContract.View mView, DbBaseOperate<SimpleSong> dbOperator) {
        this.mView = mView;
        mDbOperator = dbOperator;
    }

    @Override
    public void loadLocalMusic() {
        mView.getMyLoaderManager().initLoader(URL_LOAD_LOCAL_MUSIC, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id != URL_LOAD_LOCAL_MUSIC) return null;

        return new CursorLoader(
                mView.getViewContext(),
                MEDIA_URI,
                PROJECTIONS,
                WHERE,
                null,
                ORDER_BY
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Observable.just(cursor)
                .flatMap(new Func1<Cursor, Observable<List<SimpleSong>>>() {
                    @Override
                    public Observable<List<SimpleSong>> call(Cursor cursor) {
                        List<SimpleSong> songs = new ArrayList<>();
                        if (cursor != null && cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            do {
                                SimpleSong song = cursorToMusic(cursor);
                                song.setId(Md5Utils.md5(song.getPath()));  // make sure primary unique
                                song.setHasDown(true);
                                songs.add(song);
                            } while (cursor.moveToNext());
                        }

                        return mDbOperator.add(songs);
                    }
                }).doOnNext(new Action1<List<SimpleSong>>() {
            @Override
            public void call(List<SimpleSong> simpleSongs) {
                Collections.sort(simpleSongs, new Comparator<SimpleSong>() {
                    @Override
                    public int compare(SimpleSong left, SimpleSong right) {
                        return left.getDisplayName().compareTo(right.getDisplayName());
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SimpleSong>>() {
                    @Override
                    public void onStart() {
                        mView.startScanLocalMusic();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideScanLocalMusicUI();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.showScanErrorUI();
                    }

                    @Override
                    public void onNext(List<SimpleSong> songs) {
                        mView.showScanResult(songs);
                    }
                });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Empty
    }

    private SimpleSong cursorToMusic(Cursor cursor) {
        String realPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
        File songFile = new File(realPath);
        SimpleSong song;
        if (songFile.exists()) {
            song = FileUtils.fileToMusic(songFile);
            if (song != null) {
                return song;
            }
        }
        song = new SimpleSong();
        song.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));

        String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
        if (displayName.endsWith(".mp3")) {
            displayName = displayName.substring(0, displayName.length() - 4);
        }
        song.setDisplayName(displayName);
        song.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
        song.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
        song.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
        song.setPath(realPath);
        return song;
    }

}
