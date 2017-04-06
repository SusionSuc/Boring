package com.susion.boring.read.mvp.contract;

import com.susion.boring.base.view.IView;
import com.susion.boring.read.mvp.entity.DailyNews;
import com.susion.boring.read.mvp.entity.NewsDetail;

import java.util.Date;
import java.util.List;

/**
 * Created by susion on 17/3/9.
 */
public interface ZhiHuDailyContract {

    int LATEST_NEWS = 0;
    int AFTER_DAY_NEWS = 1;

    interface View extends IView {
        void addNewsData(List<NewsDetail> news);

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
