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
    public abstract String getTitle();
}
