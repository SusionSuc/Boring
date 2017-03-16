package com.susion.boring.music.mvp.contract;

import com.susion.boring.base.mvp.view.IView;
import com.susion.boring.music.mvp.model.Song;

/**
 * Created by susion on 17/3/7.
 */
public interface PlayMusicPageContract {

    interface View extends IView{
        void refreshLikeStatus(boolean like);
    }

    interface Presenter{
        void doLikeOrDisLikeMusic(Song song);
    }
}
