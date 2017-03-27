package com.susion.boring.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by susion on 17/3/27.
 */
public class PictureLoadHelper {

    private static final int MAX_ERROR_TIME = 50;
    private static Map<String, Integer> mCategoryLoadErrorGraphic = new HashMap<>();

    public static Set<String> mDiscardCategory = new HashSet<>();

    public static void addLoadErrorTimeForType(String mTypeId) {
        if (mCategoryLoadErrorGraphic.containsKey(mTypeId)) {
            int errorTime = mCategoryLoadErrorGraphic.get(mTypeId);
            if (errorTime > MAX_ERROR_TIME) {
                mDiscardCategory.add(mTypeId);
            }

            mCategoryLoadErrorGraphic.put(mTypeId, errorTime + 1);
            return;
        }
        mCategoryLoadErrorGraphic.put(mTypeId, 0);
    }

}
