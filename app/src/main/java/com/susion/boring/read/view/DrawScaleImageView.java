package com.susion.boring.read.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by susion on 17/3/19.
 * thinking by https://github.com/githubwing/DragPhotoView
 */
public class DrawScaleImageView extends SimpleDraweeView {

    private Paint mPaint;
    private float mDownX;    // downX
    private float mDownY;   // down Y
    private float mTranslateY;
    private float mTranslateX;
    private float mScale = 1;
    private int mWidth;
    private int mHeight;
    private float mMinScale = 0.3f;
    private int mAlpha = 255;
    private long mTime;
    private final static int MAX_TRANSLATE_Y = 500;
    private final static long DURATION = 300;

    public DrawScaleImageViewListener mListener;

    //is event on PhotoView
    private boolean isTouchEvent = false;

    public DrawScaleImageView(Context context) {
        this(context, null);
    }

    public DrawScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAlpha(mAlpha);
        canvas.drawRect(0, 0, 2000, 3000, mPaint);
        canvas.translate(mTranslateX, mTranslateY);
        canvas.scale(mScale, mScale, mWidth / 2, mHeight / 2);
        super.onDraw(canvas);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTranslateY == 0 && mTranslateX != 0) { //in viewpager
                    //如果不消费事件，则不作操作
                    if (!isTouchEvent) {
                        restoreImageState();
                    }
                }

                //single finger drag  down
                if (mTranslateY >= 0 && event.getPointerCount() == 1) {
                    onActionMove(event);

                    //如果有上下位移 则不交给viewpager
                    if (mTranslateY != 0) {
                        isTouchEvent = true;
                    }
                    return true;
                }

                //防止下拉的时候双手缩放
                if (mTranslateY >= 0 && mScale < 0.95) {
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mScale < 0.4) {
                    if (mListener != null) {
                        mListener.onExitViewImage();
                    }
                } else {
                    restoreImageState();
                }
                break;
        }

        return true;
    }


    private void restoreImageState() {
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(restoreAlpha(), restoreScale(), restoreX(), restoreY());
        animationSet.setDuration(300);
        animationSet.start();
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mListener != null) {
                    mListener.onRestoreImageState();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void onActionMove(MotionEvent event) {
        float moveY = event.getY();
        float moveX = event.getX();
        mTranslateX = moveX - mDownX;
        mTranslateY = moveY - mDownY;

        //保证上划到到顶还可以继续滑动
        if (mTranslateY < 0) {
            mTranslateY = 0;
        }

        float percent = mTranslateY / MAX_TRANSLATE_Y;
        if (mScale >= mMinScale && mScale <= 1f) {
            mScale = 1 - percent;

            mAlpha = (int) (255 * (1 - percent));
            if (mAlpha > 255) {
                mAlpha = 255;
            } else if (mAlpha < 0) {
                mAlpha = 0;
            }

            if (mListener != null) {
                mListener.onScaleChange(mAlpha);
            }
        }
        if (mScale < mMinScale) {
            mScale = mMinScale;
        } else if (mScale > 1f) {
            mScale = 1;
        }

        invalidate();
    }


    private ValueAnimator restoreAlpha() {
        final ValueAnimator animator = ValueAnimator.ofInt(mAlpha, 255);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAlpha = (int) valueAnimator.getAnimatedValue();
                if (mListener != null) {
                    mListener.onScaleChange(mAlpha);
                }
            }
        });
        return animator;
    }

    private ValueAnimator restoreY() {
        final ValueAnimator animator = ValueAnimator.ofFloat(mTranslateY, 0);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTranslateY = (float) valueAnimator.getAnimatedValue();
            }
        });

        return animator;
    }

    private ValueAnimator restoreX() {
        final ValueAnimator animator = ValueAnimator.ofFloat(mTranslateX, 0);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTranslateX = (float) valueAnimator.getAnimatedValue();
            }
        });
        return animator;
    }

    private ValueAnimator restoreScale() {
        final ValueAnimator animator = ValueAnimator.ofFloat(mScale, 1);
        animator.setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mScale = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        return animator;
    }

    public interface DrawScaleImageViewListener {
        void onScaleChange(int alpha);

        void onExitViewImage();

        void onRestoreImageState();
    }

    public void setScaleListener(DrawScaleImageViewListener mListener) {
        this.mListener = mListener;
    }

}
