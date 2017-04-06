package com.susion.boring.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;


/**
 * Created by susion on 17/2/13.
 */
public class BroadcastUtils {
    public static void sendIntentAction(Context context, String action) {
        Intent intent = new Intent(action);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
