package com.susion.boring.base;

import android.app.Activity;

import com.susion.boring.R;
import com.susion.boring.utils.StatusBarUtil;

/**
 * Created by susion on 17/1/19.
 */
public class BaseActivity extends Activity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
}
