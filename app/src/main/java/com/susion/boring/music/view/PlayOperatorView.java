package com.susion.boring.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.susion.boring.R;
import com.susion.boring.music.mvp.contract.MusicServiceContract;
import com.susion.boring.utils.UIUtils;

/**
 * Created by susion on 17/2/21.
 */
public class PlayOperatorView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private ImageView mIvCircle;
    private ImageView mIvRandom;
    private ImageView mIvLike;
    private ImageView mIvList;

    private OnItemClickListener itemClickListener;

    private boolean mPseudoLike;
    private int mPseudoMode;

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
        View.inflate(mContext, R.layout.view_play_operator, this);
        findView();
        initView();
    }

    private void findView() {
        mIvCircle = (ImageView) findViewById(R.id.view_play_operator_iv_circle);
        mIvRandom = (ImageView) findViewById(R.id.view_play_operator_iv_random);
        mIvList = (ImageView) findViewById(R.id.view_play_operator_iv_list);
        mIvLike = (ImageView) findViewById(R.id.view_play_operator_iv_like);
    }

    private void initView() {
        mIvCircle.setOnClickListener(this);
        mIvList.setOnClickListener(this);
        mIvRandom.setOnClickListener(this);
        mIvLike.setOnClickListener(this);

        mPseudoLike = false;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener == null) {
            return;
        }
        boolean isSameMode;
        switch (v.getId()) {
            case R.id.view_play_operator_iv_circle:
                isSameMode = mPseudoMode == MusicServiceContract.PlayQueueControlPresenter.CIRCLE_MODE;
                if (isSameMode) {
                    mPseudoMode = MusicServiceContract.PlayQueueControlPresenter.QUEUE_MODE;
                } else {
                    mPseudoMode = MusicServiceContract.PlayQueueControlPresenter.CIRCLE_MODE;
                }
                itemClickListener.onCirclePlayItemClick(mPseudoMode);
                refreshPlayMode(mPseudoMode);
                break;
            case R.id.view_play_operator_iv_random:
                isSameMode = mPseudoMode == MusicServiceContract.PlayQueueControlPresenter.RANDOM_MODE;
                if (isSameMode) {
                    mPseudoMode = MusicServiceContract.PlayQueueControlPresenter.QUEUE_MODE;
                } else {
                    mPseudoMode = MusicServiceContract.PlayQueueControlPresenter.RANDOM_MODE;
                }
                itemClickListener.onRandomPlayItemClick(mPseudoMode);
                refreshPlayMode(mPseudoMode);
                break;
            case R.id.view_play_operator_iv_list:
                itemClickListener.onMusicListClick();
                break;
            case R.id.view_play_operator_iv_like:
                mPseudoLike = !mPseudoLike;
                UIUtils.refreshLikeStatus(mIvLike, mPseudoLike);
                itemClickListener.onLikeItemClick(mPseudoLike);
                break;
        }
    }

    public void refreshPlayMode(int mode) {
        mIvCircle.setImageResource(R.mipmap.ic_play_operator_circle);
        mIvRandom.setImageResource(R.mipmap.ic_play_operator_random);

        if (mode == MusicServiceContract.PlayQueueControlPresenter.CIRCLE_MODE) {
            mIvCircle.setImageResource(R.mipmap.ic_play_operator_circle_enable);
        }

        if (mode == MusicServiceContract.PlayQueueControlPresenter.RANDOM_MODE) {
            mIvRandom.setImageResource(R.mipmap.ic_play_operator_random_enable);
        }
    }

    public void hideNextPlay() {
        mIvList.setVisibility(GONE);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void refreshLikeStatus(Boolean flag) {
        UIUtils.refreshLikeStatus(mIvLike, flag);
    }


    public interface OnItemClickListener {
        void onCirclePlayItemClick(int mode);

        void onRandomPlayItemClick(int mode);

        void onLikeItemClick(boolean like);

        void onMusicListClick();
    }

}
