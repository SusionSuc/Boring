package com.susion.boring.music.presenter.itf;

/**
 * Created by susion on 17/2/17.
 */
public interface IFileDownLoadView {
    void errorDownTask(IFileDownLoadPresenter.DownTask view);

    void updateDownTaskProgress(IFileDownLoadPresenter.DownTask view);

    void successDownTask(IFileDownLoadPresenter.DownTask mCurrentTask);
}
