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

            String msg;
            if (throwable instanceof ConnectException) {
                msg = "网络有点不通畅哎";
            } else if (throwable instanceof SocketTimeoutException) {
                msg = "网络有点不通畅哎";
            } else if (throwable instanceof HttpException) {
                HttpException exception = (HttpException) throwable;
                if (exception.code() >= 300 && exception.code() < 400) {
                    msg = "网路迷路了哎";
                } else if (exception.code() >= 400 && exception.code() < 500) {
                    msg = "访问的资源好像不见了";
                } else if (exception.code() >= 500 && exception.code() < 600) {
                    msg = "服务器好像挂掉了哎";
                } else {
                    msg = "出现了未知错误哎";
                }
            } else {
                msg = "出现了未知错误哎";
            }
            ToastUtils.showShort(msg);
        }
    }
}
