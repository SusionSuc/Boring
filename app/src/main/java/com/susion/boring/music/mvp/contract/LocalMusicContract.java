package com.susion.boring.music.mvp.contract;

import android.app.LoaderManager;
import com.susion.boring.base.view.IView;
import com.susion.boring.music.mvp.model.SimpleSong;

import java.util.List;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/13/16
 * Time: 8:32 PM
 * Desc: LocalMusicContract
 */
/* package */ public interface LocalMusicContract {

    interface View extends IView {
        LoaderManager getMyLoaderManager();

        void showScanResult(List<SimpleSong> songs);

        void showScanErrorUI();

        void hideScanLocalMusicUI();

        void startScanLocalMusic();
    }

    interface Presenter{
        void loadLocalMusic();
    }
}
