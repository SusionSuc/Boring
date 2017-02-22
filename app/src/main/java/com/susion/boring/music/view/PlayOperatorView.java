package com.susion.boring.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.susion.boring.R;

/**
 * Created by susion on 17/2/21.
 */
public class PlayOperatorView  extends LinearLayout{

    private Context mContext;

    public PlayOperatorView(Context context) {
        super(context);
        init();
    }


    public PlayOperatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mContext = getContext();
        View.inflate(mContext, R.layout.view_play_control, this);
    }


}
