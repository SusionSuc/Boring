package com.susion.boring.music.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.utils.TransitionHelper;

public class LocalMusicActivity extends BaseActivity {

    public static void start(Activity context) {
        Intent intent = new Intent(context, LocalMusicActivity.class);
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(context, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(context, pairs);
        context.startActivity(intent, transitionActivityOptions.toBundle());
    }

    public static void start(Activity mContext, View transitionView) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(mContext, false,
                new Pair<>(transitionView, mContext.getResources().getString(R.string.transition_name_tool_bar)));
        Intent i = new Intent(mContext, LocalMusicActivity.class);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mContext, pairs);
        mContext.startActivity(i, transitionActivityOptions.toBundle());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_local_music;
    }

    @Override
    public void findView() {

    }

    @Override
    public void initView() {
        mToolBar.setTitle("本地音乐");
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initTransitionAnim() {
        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        Transition transition = buildEnterTransition();
        getWindow().setEnterTransition(transition);
    }

    private Transition buildEnterTransition() {
        Explode enterTransition = new Explode();
        enterTransition.setDuration(500);
        return enterTransition;
    }
}
