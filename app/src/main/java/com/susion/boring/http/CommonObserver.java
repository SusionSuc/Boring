package com.susion.boring.http;

import com.susion.boring.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.functions.Action1;


public abstract class CommonObserver<T> implements Observer<T> {


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        new CommonErrorAction().call(e);
    }

    public class CommonErrorAction implements Action1<Throwable> {
        @Override
        public void call(Throwable throwable) {
            if (throwable == null) {
                return;
            }

            if (throwable instanceof ConnectException) {
                showNetError();
            } else if (throwable instanceof SocketTimeoutException) {
                showNetError();
            } else if (throwable instanceof HttpException) {
                showNetError();
            } else {
                showUnKnowError();
            }
        }
    }

    private void showNetError() {
        ToastUtils.showShort("网络好像不通畅哦....");
    }

    private void showUnKnowError() {
        ToastUtils.showShort(" -_-,  出现了位置错误");
    }

}
