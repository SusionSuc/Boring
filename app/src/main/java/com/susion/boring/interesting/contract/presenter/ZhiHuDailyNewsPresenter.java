package com.susion.boring.interesting.contract.presenter;

import com.susion.boring.http.APIHelper;
import com.susion.boring.interesting.contract.ZhiHuDailyContract;
import com.susion.boring.interesting.model.Banner;
import com.susion.boring.interesting.model.DailyNews;
import com.susion.boring.interesting.view.BannerView;
import com.susion.boring.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/9.
 */
public class ZhiHuDailyNewsPresenter implements ZhiHuDailyContract.Presenter{

    private final int BANNER_NUMBER = 6;
    private ZhiHuDailyContract.View mView;
    private Date mCurrentDate;
    private boolean mIsLatest;

    public ZhiHuDailyNewsPresenter(ZhiHuDailyContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(final int page) {
        if (page == ZhiHuDailyContract.LATEST_NEWS) {
            mIsLatest = true;
            requestForLatestNews(page);
            return;
        }

        mIsLatest = false;
        requestForFixDateNews(page);
    }

    private void requestForFixDateNews(final int page) {
        if (mCurrentDate == null) {
            return;
        }

        mCurrentDate.setTime(mCurrentDate.getTime() - 1000 * 60 * 60 * page * 24);
        String date = TimeUtils.formatDate(mCurrentDate, "yyyyMMdd");
        APIHelper.getZhiHuService()
                .getFixDateNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DailyNews dailyNews) {
                        if (dailyNews != null) {
                            mView.addNewsData(addHeaderTitle(dailyNews.getStories()));
                        }
                    }
                });

    }

    private List<DailyNews.StoriesBean> addHeaderTitle(List<DailyNews.StoriesBean> stories) {
        for (DailyNews.StoriesBean bean : stories) {
            bean.setShowTitle(mIsLatest);
            bean.setHeaderTitle(mIsLatest ? "今日新闻" : TimeUtils.getDateCnDescForZhiHu(mCurrentDate));
        }
        return stories;
    }

    private void requestForLatestNews(final int page) {
        APIHelper.getZhiHuService()
                .getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int b = 0;
                    }

                    @Override
                    public void onNext(DailyNews dailyNews) {
                        if (dailyNews != null) {
                            mView.addNewsData(addHeaderTitle(dailyNews.getStories()));
                            if (page == 0) {
                                mView.setDataForViewPage(pickUpPictures(dailyNews));
                            }
                        }
                    }
                });
    }

    @Override
    public List<BannerView> getBannerViews(List<Banner> banners) {

        List<BannerView> mBannerView = new ArrayList<>();
        for (Banner banner : banners){
            BannerView view = new BannerView(mView.getContext(), banner);
            view.setTitle(banner.title);
            view.setImgUrl(banner.imgUrl);
            mBannerView.add(view);
        }
        return mBannerView;
    }

    @Override
    public void setCurrentDate(Date date) {
        mCurrentDate = date;
    }

    private List<Banner> pickUpPictures(DailyNews dailyNews) {
        List<DailyNews.StoriesBean> stories = dailyNews.getStories();
        List<Banner> banners = new ArrayList<>();

        if (stories.size() >= BANNER_NUMBER) {
            for (int i=0; i<stories.size(); i++) {
                if (banners.size() >= BANNER_NUMBER) {
                    break;
                }

                DailyNews.StoriesBean story = stories.get(i);
                if (!story.getImages().isEmpty()) {
                    Banner banner = new Banner(story.getImages().get(0), story.getId(), story.getTitle());
                    banners.add(banner);
                }
            }
        }
        return banners;
    }
}
