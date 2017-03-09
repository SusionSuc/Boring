package com.susion.boring.music.presenter.itf;

import com.susion.boring.base.IView;
import com.susion.boring.music.model.PlayList;
import com.susion.boring.music.model.PlayListDetail;

/**
 * Created by susion on 17/3/6.
 */
public interface PlayListContract {

    interface View extends IView{
        void addData(PlayListDetail playListDetail);

        void refreshPlayListLikeStatus(Boolean flag);

    }

    interface Presenter {
        void loadData(PlayList playList);

        void likePlayList(PlayList playList);

        void disLikePlayList(PlayList playList);

        void queryPlayListLikeStatus(PlayList mPlayList);
    }
}
