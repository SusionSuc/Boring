package com.susion.boring.db;

import com.litesuits.orm.BuildConfig;
import com.litesuits.orm.LiteOrm;
import com.susion.boring.base.SAppApplication;

/**
 * Created by susion on 17/2/20.
 */
public class DbManager {

    private static volatile LiteOrm sLiteOrm;
    private static final String DB_NAME = "boring.db";

    private DbManager() {
    }

    public static LiteOrm getLiteOrm() {
        if (sLiteOrm == null) {
            synchronized (DbManager.class) {
                if (sLiteOrm == null) {
                    sLiteOrm = LiteOrm.newCascadeInstance(SAppApplication.getAppContext(), DB_NAME);
                    sLiteOrm.setDebugged(BuildConfig.DEBUG);
                }
            }
        }
        return sLiteOrm;
    }
}
