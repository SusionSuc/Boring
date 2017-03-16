package com.susion.boring.interesting.mvp.contract;

import com.susion.boring.base.mvp.view.IView;
import com.susion.boring.interesting.mvp.model.Banner;
import com.susion.boring.interesting.mvp.model.DailyNews;
import com.susion.boring.interesting.view.BannerView;

import java.util.Date;
import java.util.List;

/**
 * Created by susion on 17/3/9.
 */
public interface ZhiHuDailyContract {

    int LATEST_NEWS = 0;
    int AFTER_DAY_NEWS = 1;
    int BEFORE_DAY_NEWS = -1;

    interface View extends IView {
        void addNewsData(List<DailyNews.StoriesBean> news);
        void setDataForViewPage(List<DailyNews.TopStoriesBean> banners);
    }


    interface Presenter {
        void loadData(int page);

        void setCurrentDate(Date date);
    }

    interface DailyNewsStickHeader {
        String getTitle(int position);

        int getHeaderColor(int position);

        boolean isShowTitle(int position);
    }
}
