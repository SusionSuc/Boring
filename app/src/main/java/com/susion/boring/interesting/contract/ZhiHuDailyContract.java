package com.susion.boring.interesting.contract;

import com.susion.boring.base.ui.IView;
import com.susion.boring.interesting.model.Banner;
import com.susion.boring.interesting.model.DailyNews;
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

    interface  View extends IView{
        void setDataForViewPage(List<Banner> banners);
        void addNewsData(List<DailyNews.StoriesBean> news);
    }


    interface Presenter{
        void loadData(int page);

        List<BannerView> getBannerViews(List<Banner> banners);

        void setCurrentDate(Date date);
    }

    interface DailyNewsStickHeader{
        String getTitle(int position);
        int getHeaderColor(int position);
        void setNewTitle(String newTitle);
        boolean isShowTitle(int position);
    }
}
