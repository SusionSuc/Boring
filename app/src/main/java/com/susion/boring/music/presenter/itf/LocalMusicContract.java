package com.susion.boring.music.presenter.itf;

import android.app.LoaderManager;
import android.content.Context;


import com.susion.boring.base.BasePresenter;
import com.susion.boring.base.BaseView;
import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.music.model.Song;

import java.util.List;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/13/16
 * Time: 8:32 PM
 * Desc: LocalMusicContract
 */
/* package */ public interface LocalMusicContract {

    interface View extends BaseView<Presenter> {
        Context getContext();

        LoaderManager getMyLoaderManager();

        void showScanResult(List<SimpleSong> songs);

        void showScanErrorUI();

        void hideScanLocalMusicUI();

        void startScanLocalMusic();
    }

    interface Presenter extends BasePresenter {
        void loadLocalMusic();
    }
}
