package com.susion.boring.music.view;

import android.content.Context;
import android.os.Debug;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.susion.boring.R;

/**
 * Created by susion on 17/1/23.
 */
public class WaterWaveRotateImageView extends LinearLayout{


    private Context mContext;

    public WaterWaveRotateImageView(Context context) {
        super(context);
        init(context);
    }
    public WaterWaveRotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaterWaveRotateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        initView();
    }

    private void initView() {
        View.inflate(mContext, R.layout.view_water_wave_rotate_iv, this);
    }
}
