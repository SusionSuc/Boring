package com.susion.boring.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by susion on 17/2/7.
 */
public class SystemOperationUtils {

    public static void closeSystemKeyBoard(Activity context){
        InputMethodManager imm =  (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }
}
