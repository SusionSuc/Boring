package com.susion.boring.interesting.mvp.presenter;

import com.susion.boring.db.DbManager;
import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.http.APIHelper;
import com.susion.boring.interesting.mvp.contract.ZhiHuEssayContract;
import com.susion.boring.interesting.mvp.model.DailyNews;
import com.susion.boring.interesting.mvp.model.NewsDetail;
import com.susion.boring.utils.FileUtils;
import com.susion.boring.utils.StringUtils;
import com.susion.boring.utils.TimeUtils;
import com.susion.boring.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/14.
 */
public class ZhiHuEssayPresenter implements ZhiHuEssayContract.Presenter {

    private ZhiHuEssayContract.View mView;
    private ZhiHuEssayContract.EssayQueue mEssayQueue;
    private Date mCurrentDate;
    private NewsDetail mNewsDetail;

    public ZhiHuEssayPresenter(ZhiHuEssayContract.View mView, Date date) {
        this.mView = mView;
        mEssayQueue = new ZhiHuEssayQueuePresenter();
        mCurrentDate = date;
        requestIdsForDate();
    }

    @Override
    public void loadEssay(String essayId) {
        APIHelper.getZhiHuService()
                .getEssayContent(essayId)
                .flatMap(new Func1<NewsDetail, Observable<NewsDetail>>() {
                    @Override
                    public Observable<NewsDetail> call(final NewsDetail dailyNews) {
                        return APIHelper.getZhiHuService()  //load css style
                                .getEssayCSS(dailyNews.getCss().get(0))
                                .map(new Func1<ResponseBody, NewsDetail>() {
                                    @Override
                                    public NewsDetail call(ResponseBody responseBody) {
                                        String css = FileUtils.getStringFromInputStream(responseBody.byteStream());
                                        String head = StringUtils.getHTMLHead(StringUtils.getCSSStyle(css));
                                        dailyNews.htmlStr = StringUtils.getHtmlString(head, dailyNews.getBody());
                                        return dailyNews;
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsDetail dailyNews) {
                        mNewsDetail = dailyNews;
                        mView.setHeaderImage(dailyNews.getImage());
                        mView.showEssayContent(StringUtils.adjustEsssayHtmlStyle(dailyNews.htmlStr));
                    }
                });
    }


    private void requestIdsForDate() {
        String date = TimeUtils.formatDate(mCurrentDate, "yyyyMMdd");
        getDailyNews(date);
    }

    private void getDailyNews(String date) {
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
                        if (dailyNews == null) {
                            return;
                        }
                        initEssayQueue(dailyNews);
                    }
                });
    }

    private void initEssayQueue(DailyNews dailyNews) {
        List<String> ids = new ArrayList<>();
        for (DailyNews.StoriesBean bean : dailyNews.getStories()) {
            ids.add(bean.getId());
        }
        mEssayQueue.loadEssayQueue(ids);
    }

    @Override
    public void loadPreEssay() {
        String id = mEssayQueue.getPreEssayId();
        if (id == null) {
            ToastUtils.showShort("这是 " + TimeUtils.formatDate(mCurrentDate, "yyyy年MM月dd日 ") + " 的第一篇新闻了");
            return;
        }
        loadEssay(id);
    }

    @Override
    public void loadNextEssay() {
        String id = mEssayQueue.getNextEssayId();
        if (id == null) {
            ToastUtils.showShort("这是 " + TimeUtils.formatDate(mCurrentDate, "yyyy年MM月dd日 ") + " 的最后一篇新闻了");
            return;
        }
        loadEssay(id);
    }

    @Override
    public void likeEssay() {
        mNewsDetail.isLike = !mNewsDetail.isLike;
        DbBaseOperate<NewsDetail> dbOperate = new DbBaseOperate<>(DbManager.getLiteOrm(), mView.getViewContext(), NewsDetail.class);
        dbOperate.add(mNewsDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean success) {
                        if (!success) {
                            mNewsDetail.isLike = !mNewsDetail.isLike;  //persistent failed
                        }
                        mView.refreshLikeStatus(mNewsDetail.isLike);
                    }
                });
    }

}
