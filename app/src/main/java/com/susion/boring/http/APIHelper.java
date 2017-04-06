package com.susion.boring.http;

import com.susion.boring.http.service.JokeService;
import com.susion.boring.http.service.MusicServices;
import com.susion.boring.http.service.PictureService;
import com.susion.boring.http.service.ZhiHuService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/1/20.
 */
public class APIHelper {

    public static final int REQUEST_SUCCESS = 200;
    private static Retrofit mRetrofit;
    private static OkHttpClient mClient;
    private HashMap<Class, Object> mServicesMap = new HashMap<>();
    private static APIHelper sInstance;

    private APIHelper() {

    }

    private synchronized static APIHelper getInstance() {
        if (sInstance == null) {
            sInstance = new APIHelper();
            initRetrofit();
        }
        return sInstance;
    }

    private static void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createClient())
                .baseUrl("http://www.susion.site")
                .build();
    }

    private static OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        mClient = builder.build();
        return mClient;
    }


    public static OkHttpClient.Builder createBaseClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder;
    }


    public static <T> void subscribeSimpleRequest(Observable<T> observable, CommonObserver<T> observer) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }


    private synchronized <T> T getService(Class<T> c) {
        if (!mServicesMap.containsKey(c)) {
            mServicesMap.put(c, mRetrofit.create(c));
        }
        return (T) mServicesMap.get(c);
    }

    public static MusicServices getMusicServices() {
        return getInstance().getService(MusicServices.class);
    }


    public static ZhiHuService getZhiHuService() {
        return getInstance().getService(ZhiHuService.class);
    }


    public static JokeService getJokeService() {
        return getInstance().getService(JokeService.class);
    }

    public static PictureService getPictureService() {
        return getInstance().getService(PictureService.class);
    }
}
