package com.susion.boring.interesting.contract;

import java.util.Date;

/**
 * Created by susion on 17/3/14.
 */
public interface ZhiHuEssayContract {

    interface View {
        void showEssayContent(String contentHtml);
        void setHeaderImage(String url);
    }

    interface Presenter{
        void loadEssay(String essayId);

        void loadPreEssay();

        void loadNextEssay();

        void likeEssay(String mEssayId);
    }
}
