package com.susion.boring.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.susion.boring.R;

/**
 * Created by susion on 17/1/23.
 */
public class MusicPlayControlView extends LinearLayout implements View.OnClickListener{

    private Context mContext;
    private ImageView mIvStartStop;
    private ImageView mIvMore;
    private ImageView mIvNext;
    private ImageView mIvPre;
    private ImageView mIVPattern;

    private MusicPlayerControlViewItemClickListener listener;
    public MusicPlayControlView(Context context) {
        super(context);
        init(context);
    }
    public MusicPlayControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MusicPlayControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View.inflate(mContext, R.layout.view_music_play_control, this);
        findView();
    }

    private void findView() {
        mIvStartStop = (ImageView) findViewById(R.id.view_music_player_start_or_stop);
        mIvMore = (ImageView) findViewById(R.id.view_music_player_list);
        mIvNext = (ImageView) findViewById(R.id.view_music_player_next);
        mIvPre = (ImageView) findViewById(R.id.view_music_player_pre);
        mIVPattern = (ImageView) findViewById(R.id.view_music_player_pattern);

        mIvMore.setOnClickListener(this);
        mIvStartStop.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
        mIvPre.setOnClickListener(this);
        mIVPattern.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (listener == null) {
            return;
        }

        int id = view.getId();
        switch (id){
            case R.id.view_music_player_list:
                listener.onMoreItemClick();
                break;
            case R.id.view_music_player_next:
                listener.onNextItemClick();
                break;
            case R.id.view_music_player_pattern:
                listener.onPatternItemClick();
                break;
            case R.id.view_music_player_pre:
                listener.onPreItemClick();
                break;
            case R.id.view_music_player_start_or_stop:
                listener.onStartOrStartItemClick();
                break;
        }
    }

    public interface MusicPlayerControlViewItemClickListener{
        void onMoreItemClick();
        void onNextItemClick();
        void onPreItemClick();
        void onPatternItemClick();
        void onStartOrStartItemClick();

    }

    void setOnControlItemClickListener(MusicPlayerControlViewItemClickListener listener){
        this.listener = listener;
    }


}
