package com.susion.boring.music.presenter.itf;

import com.susion.boring.music.model.DownTask;

/**
 * Created by susion on 17/2/17.
 */
public interface IFileDownLoadView {
    void errorDownTask(DownTask view);

    void updateDownTaskProgress(DownTask view);

    void successDownTask(DownTask mCurrentTask);
}
