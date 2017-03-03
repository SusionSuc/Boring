package com.susion.boring;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.music.service.MusicPlayerService;
import com.susion.boring.utils.FileUtils;

/**
 * Created by susion on 17/1/17.
 */
public class SAppApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        FileUtils.initAppDir();
        MusicInstruction.startMusicPlayService(this);  //start music service
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
    }

    public static Context getAppContext() {
        return  sContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService(new Intent(MusicPlayerService.SERVICE_ACTION));
    }
}
