package com.susion.boring.interesting.contract.presenter;

import com.susion.boring.http.APIHelper;
import com.susion.boring.interesting.contract.ZhiHuEssayContract;
import com.susion.boring.interesting.model.NewsDetail;
import com.susion.boring.utils.FileUtils;
import com.susion.boring.utils.StringUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/14.
 */
public class ZhiHuEssayPresenter implements ZhiHuEssayContract.Presenter{

    private ZhiHuEssayContract.View mView;

    public ZhiHuEssayPresenter(ZhiHuEssayContract.View mView) {
        this.mView = mView;
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
                        mView.setHeaderImage(dailyNews.getImage());
                        mView.showEssayContent(StringUtils.adjustEsssayHtmlStyle(dailyNews.htmlStr));
                    }
                });
    }

    @Override
    public void loadPreEssay() {

    }

    @Override
    public void loadNextEssay() {

    }

    @Override
    public void likeEssay(String mEssayId) {

    }
}
