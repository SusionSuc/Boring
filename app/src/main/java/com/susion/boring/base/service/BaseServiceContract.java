package com.susion.boring.base.service;

/**
 * Created by susion on 17/3/14.
 */
public interface BaseServiceContract {

    void initService();

    void onTaskMoved();

    void onDestroy();

    interface ReceiverPresenter {
        void releaseResource();
    }
}
