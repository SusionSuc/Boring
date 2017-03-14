package com.susion.boring.interesting.service;

import android.content.Context;

import com.susion.boring.base.service.ServiceContract;

import java.util.Date;
import java.util.List;

/**
 * Created by susion on 17/3/14.
 *
 * main manager essay list
 */
public class ZhiHuService implements ServiceContract {

    private Context mContext;
    private List<String> mEssayIds;
    private Date mCurrentDate;

    public ZhiHuService(Context context) {
        mContext = context;
    }

    @Override
    public void initService() {

    }

    @Override
    public void onTaskMoved() {

    }

    @Override
    public void onDestroy() {

    }


}
