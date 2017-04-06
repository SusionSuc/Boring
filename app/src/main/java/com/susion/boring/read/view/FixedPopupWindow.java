package com.susion.boring.read.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.susion.boring.base.SAppApplication;


/**
 * Created by susion on 11/7/16.
 */
public class FixedPopupWindow extends PopupWindow {
    public FixedPopupWindow(Context context) {
        super(context);
    }

    public FixedPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedPopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FixedPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FixedPopupWindow(View contentView) {
        super(contentView);
    }

    public FixedPopupWindow() {
        super();
    }

    public FixedPopupWindow(int width, int height) {
        super(width, height);
    }

    public FixedPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public FixedPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }


    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (isShowing() || getContentView() == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= 24) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);

            Context context = SAppApplication.getAppContext();
            if (context instanceof Activity) {
                showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.NO_GRAVITY, xoff, yoff + a[1] + anchor.getHeight());
            } else {
                super.showAsDropDown(anchor, xoff, yoff, gravity);
            }
        } else {
            super.showAsDropDown(anchor, xoff, yoff, gravity);
        }
    }

}
