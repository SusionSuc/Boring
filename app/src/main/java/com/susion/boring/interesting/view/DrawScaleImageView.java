package com.susion.boring.interesting.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by susion on 17/3/19.
 * thinking by https://github.com/githubwing/DragPhotoView
 */
public class DrawScaleImageView extends SimpleDraweeView {


    private float mScaleX;
    private float mScaleY;
    private float mTranslationX;
    private float mTranslationY;

    public DrawScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void startEntryScaleAnimation(Rect mOriginIvPos) {
        calculateScaleValue(mOriginIvPos);
        performScaleAnimation();
    }

    private void calculateScaleValue(Rect mOriginIvPos) {
        int originLeft = mOriginIvPos.left;
        int originTop = mOriginIvPos.top;
        int originWidth = mOriginIvPos.right;
        int originHeight = mOriginIvPos.bottom;

        int[] location = new int[2];
        getLocationOnScreen(location);
        float mCenterX = location[0] + getWidth() / 2;
        float mCenterY = location[1] + getHeight() / 2;

        mScaleX = (float) originWidth / getWidth();
        mScaleY = (float) originHeight / getHeight();
        mTranslationX = originLeft - mCenterX;
        mTranslationY = originTop - mCenterY;


        setTranslationX(mTranslationX);
        setTranslationX(mTranslationY);

        setScaleX(mScaleX);
        setScaleY(mScaleY);
    }

    private void performScaleAnimation() {
        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(getX(), 0);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                DrawScaleImageView.this.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(getY(), 0);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                DrawScaleImageView.this.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();

        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(mScaleY, 1);
        scaleYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                DrawScaleImageView.this.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleYAnimator.setDuration(300);
        scaleYAnimator.start();

        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(mScaleX, 1);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                DrawScaleImageView.this.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleXAnimator.setDuration(300);
        scaleXAnimator.start();
    }

}
