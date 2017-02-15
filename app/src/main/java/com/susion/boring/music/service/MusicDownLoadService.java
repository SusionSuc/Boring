package com.susion.boring.music.service;

import com.susion.boring.API.APIHelper;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by susion on 17/2/15.
 */
public class MusicDownLoadService {

    private static List<DownDecInfo> mList = new ArrayList<>();
    private static  MusicDownLoadService sInstance;
    private static DownEngine mEngine;

    private MusicDownLoadService(){

    }


    public static MusicDownLoadService getInstance(){
        synchronized (MusicDownLoadService.class){
            if (sInstance == null) {
                mEngine = new DownEngine();
                sInstance = new MusicDownLoadService();
            }
        }
        return sInstance;
    }


    public static void add(String uri, String id){
        mList.add(new DownDecInfo(uri, id));
    }

    public void remove(String id){

    }

    private static class DownEngine {
        private boolean mIsPrepared;

        public DownEngine(){

        }

        public boolean isPrepared(){
            return mIsPrepared;
        }

    }


    public static class DownDecInfo {
        public String uri;
        public String id;
        public DownDecInfo(String uri, String id) {
            this.uri = uri;
            this.id = id;
        }
    }




}
