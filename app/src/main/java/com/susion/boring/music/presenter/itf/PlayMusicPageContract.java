package com.susion.boring.music.presenter.itf;

import com.susion.boring.base.IView;
import com.susion.boring.music.model.Song;

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
