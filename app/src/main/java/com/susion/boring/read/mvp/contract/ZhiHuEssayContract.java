package com.susion.boring.read.mvp.contract;

import com.susion.boring.base.view.IView;

import java.util.List;

/**
 * Created by susion on 17/3/14.
 */
public interface ZhiHuEssayContract {

    interface View extends IView {
        void showEssayContent(String contentHtml);

        void setHeaderImage(String url);

        void refreshLikeStatus(boolean status);
    }

    interface Presenter {
        void loadEssay(String essayId);

        void loadPreEssay();

        void loadNextEssay();

        void likeEssay();
    }

    interface EssayQueue {
        String getPreEssayId();

        String getNextEssayId();

        void loadEssayQueue(List<String> ids);
    }


}
