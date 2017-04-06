package com.susion.boring.music.mvp.presenter;

import com.susion.boring.music.mvp.model.DownTask;
import com.susion.boring.music.mvp.contract.FileDownContract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by susion on 17/2/17.
 */
public class FileDownloadPresenter implements FileDownContract.Presenter {

    private List<DownTask> mTasks = new ArrayList<>();
    private Set<String> mUris = new HashSet<>();
    DownTask mCurrentTask;
    private boolean mIsDowning;

    private static FileDownloadPresenter mInstance;
    private List<FileDownContract.View> mViews = new ArrayList<>();

    private FileDownloadPresenter() {

    }

    public static FileDownloadPresenter getInstance() {
        if (mInstance == null) {
            synchronized (FileDownloadPresenter.class) {
                if (mInstance == null) {
                    mInstance = new FileDownloadPresenter();
                }
            }
        }
        return mInstance;
    }


    @Override
    public boolean addDownTask(DownTask task) {
        if (mUris.contains(task.uri)) {
            return false;
        }
        mUris.add(task.uri);
        mTasks.add(task);
        startDownTask();
        return true;
    }

    @Override
    public boolean removeDownTask(DownTask task) {

        if (mTasks.isEmpty()) {
            return false;
        }

        if (task.uri.equals(mCurrentTask.uri)) {
//            OkGo.getInstance().cancelTag(task.uri);
            mTasks.remove(mCurrentTask);
            return true;
        }

        for (int i = 0; i < mTasks.size(); i++) {
            if (task.uri.equals(mTasks.get(i).uri)) {
                mTasks.remove(i);
                mUris.remove(task.uri);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<DownTask> getTaskList() {
        return mTasks;
    }

    @Override
    public DownTask getCurrentDownTask() {
        return mCurrentTask;
    }

    @Override
    public boolean stopDown(DownTask task) {
        if (!mUris.contains(task.uri)) {
            return false;
        }

//        OkGo.getInstance().cancelTag(task.uri);
        return true;
    }

    @Override
    public boolean isDowning(DownTask task) {
        if (mTasks.isEmpty()) {
            return false;
        }

        if (mCurrentTask.uri.equals(task.uri)) {
            return true;
        }
        return false;
    }


    private void startDownTask() {
        while (!mIsDowning && !mTasks.isEmpty()) {
            mCurrentTask = mTasks.get(0);
            mIsDowning = true;
            //todo down task
        }
    }

    public void addFileDownLoadView(FileDownContract.View view) {
        mViews.add(view);
    }

}
