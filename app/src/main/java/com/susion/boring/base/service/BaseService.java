package com.susion.boring.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.susion.boring.music.service.MusicPlayerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/3/14.
 */
public class BaseService extends Service {

    List<BaseServiceContract> childServices;
    public static final String SERVICE_ACTION = "BASE_SERVICE";

    @Override
    public void onCreate() {
        super.onCreate();
        childServices = new ArrayList<>();
        childServices.add(new MusicPlayerService(this));

        for (BaseServiceContract service : childServices) {
            service.initService();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        for (BaseServiceContract service : childServices) {
            service.onTaskMoved();
        }
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        for (BaseServiceContract service : childServices) {
            service.onDestroy();
        }
        super.onDestroy();
    }
}
