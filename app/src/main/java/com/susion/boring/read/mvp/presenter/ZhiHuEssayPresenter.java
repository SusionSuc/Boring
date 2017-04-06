package com.susion.boring.read.mvp.presenter;

import com.susion.boring.db.operate.DbBaseOperate;
import com.susion.boring.event.EssayDeleteFromLikeEvent;
import com.susion.boring.http.APIHelper;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.read.mvp.contract.ZhiHuEssayContract;
import com.susion.boring.read.mvp.entity.DailyNews;
import com.susion.boring.read.mvp.entity.NewsDetail;
import com.susion.boring.utils.FileUtils;
import com.susion.boring.utils.StringUtils;
import com.susion.boring.utils.TimeUtils;
import com.susion.boring.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

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
    private boolean mIsLove;
    DbBaseOperate<NewsDetail> mDbOperator;

    public ZhiHuEssayPresenter(ZhiHuEssayContract.View mView, Date date, DbBaseOperate<NewsDetail> dbOperator) {
        this.mView = mView;
        mEssayQueue = new ZhiHuEssayQueuePresenter();
        mCurrentDate = date;
        mDbOperator = dbOperator;
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
                        getLoveStatus();
                        mView.setHeaderImage(dailyNews.getImage());
                        mView.showEssayContent(StringUtils.adjustEsssayHtmlStyle(dailyNews.htmlStr));
                    }
                });
    }

    private void getLoveStatus() {
        APIHelper.subscribeSimpleRequest(mDbOperator.query(mNewsDetail.id), new CommonObserver<NewsDetail>() {
            @Override
            public void onNext(NewsDetail detail) {
                mIsLove = detail != null ? true : false;
                mView.refreshLikeStatus(mIsLove);
            }
        });

    }


    private void requestIdsForDate() {
        String date = TimeUtils.formatDate(mCurrentDate, "yyyyMMdd");
        getDailyNews(date);
    }

    private void getDailyNews(String date) {
        APIHelper.subscribeSimpleRequest(APIHelper.getZhiHuService().getFixDateNews(date), new CommonObserver<DailyNews>() {
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
        for (NewsDetail bean : dailyNews.getStories()) {
            ids.add(bean.getId());
        }
        mEssayQueue.loadEssayQueue(ids);
    }

    @Override
    public void loadPreEssay() {
        String id = mEssayQueue.getPreEssayId();
        if (id == null) {
            ToastUtils.showShort("向前..没有更多新闻了!");
            return;
        }
        loadEssay(id);
    }

    @Override
    public void loadNextEssay() {
        String id = mEssayQueue.getNextEssayId();
        if (id == null) {
            ToastUtils.showShort("向后..没有更多新闻了");
            return;
        }
        loadEssay(id);
    }

    @Override
    public void likeEssay() {
        if (mIsLove) {
            APIHelper.subscribeSimpleRequest(mDbOperator.delete(mNewsDetail), new CommonObserver<Boolean>() {
                @Override
                public void onNext(Boolean flag) {
                    ToastUtils.showShort(flag ? "已经从喜欢列表移除" : "从喜欢列表移除失败");
                    mIsLove = flag ? false : true;
                    mView.refreshLikeStatus(mIsLove);
                    if (!mIsLove) {
                        EventBus.getDefault().post(new EssayDeleteFromLikeEvent(mNewsDetail));
                    }
                }
            });
        } else {
            APIHelper.subscribeSimpleRequest(mDbOperator.add(mNewsDetail), new CommonObserver<Boolean>() {
                @Override
                public void onNext(Boolean flag) {
                    ToastUtils.showShort(flag ? "已喜欢" : "喜欢失败");
                    mIsLove = flag ? true : false;
                    mView.refreshLikeStatus(mIsLove);
                }
            });
        }
    }


}
