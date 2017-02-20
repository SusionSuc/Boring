package com.susion.boring.music.presenter.itf;

import com.susion.boring.base.BasePresenter;
import com.susion.boring.base.BaseView;
import com.susion.boring.music.model.DownTask;

import java.util.List;

/**
 * Created by susion on 17/2/20.
 */
public interface FileDownContract {
    interface View extends BaseView<Presenter> {
        void errorDownTask(DownTask view);

        void updateDownTaskProgress(DownTask view);

        void successDownTask(DownTask mCurrentTask);
    }

    interface Presenter extends BasePresenter {

        boolean addDownTask(DownTask url);

        boolean removeDownTask(DownTask url);

        List<DownTask> getTaskList();

        DownTask getCurrentDownTask();

        boolean stopDown(DownTask task);

        boolean isDowning(DownTask task);

    }
}
