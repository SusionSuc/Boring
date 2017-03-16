package com.susion.boring.music.mvp.contract;

import com.susion.boring.base.mvp.view.IView;
import com.susion.boring.music.mvp.model.PlayList;
import com.susion.boring.music.mvp.model.PlayListDetail;

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
