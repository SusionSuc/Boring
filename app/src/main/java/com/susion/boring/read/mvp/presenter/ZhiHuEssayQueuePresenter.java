package com.susion.boring.read.mvp.presenter;

import com.susion.boring.read.mvp.contract.ZhiHuEssayContract;

import java.util.List;

/**
 * Created by susion on 17/3/14.
 */
public class ZhiHuEssayQueuePresenter implements ZhiHuEssayContract.EssayQueue {

    private int mCurrentIndex;
    private List<String> mEssayIds;


    public String getPreEssayId() {
        if (mEssayIds == null || mEssayIds.isEmpty()) {
            return null;
        }

        mCurrentIndex--;
        if (mCurrentIndex >= 0 && mCurrentIndex < mEssayIds.size()) {
            return mEssayIds.get(mCurrentIndex);
        }
        return null;
    }

    public String getNextEssayId() {
        if (mEssayIds == null || mEssayIds.isEmpty()) {
            return null;
        }
        mCurrentIndex++;
        if (mCurrentIndex < mEssayIds.size() && mCurrentIndex >= 0) {
            return mEssayIds.get(mCurrentIndex);
        }

        return null;
    }

    public String getCurrentEssayId() {
        return mEssayIds.get(mCurrentIndex);
    }

    public void loadEssayQueue(List<String> ids) {
        mEssayIds = ids;
        mCurrentIndex = 0;
    }
}
