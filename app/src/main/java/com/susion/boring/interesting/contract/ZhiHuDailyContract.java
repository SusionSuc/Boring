package com.susion.boring.interesting.contract;

import com.susion.boring.base.IView;
import com.susion.boring.interesting.model.Banner;
import com.susion.boring.interesting.model.DailyNews;
import com.susion.boring.interesting.view.BannerView;

import java.util.List;

/**
 * Created by susion on 17/3/9.
 */
public interface ZhiHuDailyContract {

    interface  View extends IView{
        void setDataForViewPage(List<Banner> banners);
        void addNewsData(List<DailyNews.StoriesBean> news);
    }

    interface Presenter{
        void loadData(int page);

        List<BannerView> getBannerViews(List<Banner> banners);
    }
}
