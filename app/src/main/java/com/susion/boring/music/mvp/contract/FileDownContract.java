package com.susion.boring.music.mvp.contract;

import com.susion.boring.base.view.IView;
import com.susion.boring.music.mvp.model.DownTask;

import java.util.List;

/**
 * Created by susion on 17/2/20.
 *
 * because copyright , no use
 */
public interface FileDownContract {

    interface View extends IView {
        void errorDownTask(DownTask view);

        void updateDownTaskProgress(DownTask view);

        void successDownTask(DownTask mCurrentTask);
    }

    interface Presenter {

        boolean addDownTask(DownTask url);

        boolean removeDownTask(DownTask url);

        List<DownTask> getTaskList();

        DownTask getCurrentDownTask();

        boolean stopDown(DownTask task);

        boolean isDowning(DownTask task);
    }
}
