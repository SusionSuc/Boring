package com.susion.boring.music.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.susion.boring.music.model.DownTask;
import com.susion.boring.music.presenter.itf.IFileDownLoadPresenter;
import com.susion.boring.music.presenter.itf.IFileDownLoadView;
import com.susion.boring.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by susion on 17/2/17.
 */
public class FileDownloadPresenter implements IFileDownLoadPresenter {

    private List<DownTask> mTasks = new ArrayList<>();
    private Set<String> mUris = new HashSet<>();
    DownTask mCurrentTask;
    private boolean mIsDowning;

    private static FileDownloadPresenter mInstance;
    private List<IFileDownLoadView> mViews = new ArrayList<>();

    private FileDownloadPresenter(){

    }


    public static FileDownloadPresenter getInstance(){
        if (mInstance == null) {
            synchronized (FileDownloadPresenter.class){
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
            OkGo.getInstance().cancelTag(task.uri);
            mTasks.remove(mCurrentTask);
            return true;
        }

        for (int i=0; i<mTasks.size(); i++) {
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

        OkGo.getInstance().cancelTag(task.uri);
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
            OkGo.get(mCurrentTask.uri)
                    .tag(mCurrentTask.uri)
                    .execute(new FileCallback(mCurrentTask.taskName) {
                        @Override
                        public void onSuccess(File file, Call call, Response response) {
                            mCurrentTask.downStatus = DownTask.DOWN_SUCCESS;
                            mTasks.remove(mCurrentTask);
                            mUris.remove(mCurrentTask.uri);
                            mIsDowning = false;

                            if (FileUtils.saveFile(file, FileUtils.SD_MUSIC_DIR)) {

                            }

                            if (!mViews.isEmpty()) {
                                for (IFileDownLoadView view : mViews) {
                                    view.successDownTask(mCurrentTask);
                                }
                            }

                            startDownTask();
                        }
                        @Override
                        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            mCurrentTask.setDownInfo(currentSize, totalSize, progress, networkSpeed);

                            if (!mViews.isEmpty()) {
                                for (IFileDownLoadView view : mViews) {
                                    view.updateDownTaskProgress(mCurrentTask);
                                }
                            }
                        }
                        @Override
                        public void onError(Call call, @Nullable Response response, @Nullable Exception e) {
                            mCurrentTask.downStatus = DownTask.DOWN_ERROR;
                            mIsDowning = false;
                            if (!mViews.isEmpty()) {
                                for (IFileDownLoadView view : mViews) {
                                    view.errorDownTask(mCurrentTask);
                                }
                            }
                        }
                    });
        }
    }

   public void addFileDownLoadView(IFileDownLoadView view){
       mViews.add(view);
   }

}
