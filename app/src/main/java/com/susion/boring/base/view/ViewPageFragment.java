package com.susion.boring.base.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.ui.BaseFragment;
import com.susion.boring.utils.UIUtils;

import java.util.List;

/**
 * Created by susion on 17/3/15.
 */
public abstract class ViewPageFragment extends BaseFragment {
    private TextView mTvUpdateTip;

    public abstract String getTitle();

    @Override
    protected void findView() {
        mTvUpdateTip = (TextView) mView.findViewById(R.id.tv_update_tip);
    }

    protected void showUpdateTip(String tip) {
        mTvUpdateTip.setTranslationY(-1.0f * UIUtils.dp2Px(getContext().getResources().getDimension(R.dimen.update_tip_height)));
        mTvUpdateTip.setVisibility(View.VISIBLE);
        mTvUpdateTip.setText(tip);
        mTvUpdateTip.animate().setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTvUpdateTip.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideTipText();
                    }
                }, 500);
            }
        }).translationY(0.0f);
    }

    private void hideTipText() {
        mTvUpdateTip.animate()
                .translationY(0)
                .translationY(-1.0f * UIUtils.dp2Px(getContext().getResources().getDimension(R.dimen.update_tip_height)))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mTvUpdateTip.setVisibility(View.GONE);
                    }
                });
    }

}
