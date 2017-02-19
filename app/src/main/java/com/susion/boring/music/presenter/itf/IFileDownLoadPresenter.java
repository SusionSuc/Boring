package com.susion.boring.music.presenter.itf;

import com.susion.boring.music.model.DownTask;

import java.util.List;

/**
 * Created by susion on 17/2/17.
 */
public interface IFileDownLoadPresenter {

    boolean addDownTask(DownTask url);

    boolean removeDownTask(DownTask url);

    List<DownTask> getTaskList();

    DownTask getCurrentDownTask();

    boolean stopDown(DownTask task);

    boolean isDowning(DownTask task);


}
