package com.susion.boring.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.susion.boring.R;

/**
 * Created by susion on 17/1/19.
 */
public class RVUtils {

    public static LinearLayoutManager getLayoutManager(Context context, int orientation){
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(orientation);
        return manager;
    }


    public  static  RecyclerView.ItemDecoration getItemDecorationDivider(Context context, int color, int divideHeight){
        return new SimpleDividerDecoration(context, color, divideHeight);
    }


    public  static  RecyclerView.ItemDecoration getItemDecorationDivider(Context context, int color, int divideHeight, int dividerNumber, int leftMargin){
        return new SimpleDividerDecoration(context, color, divideHeight, dividerNumber, leftMargin);
    }

    public  static  class SimpleDividerDecoration extends RecyclerView.ItemDecoration {
        public Paint mDividerPaint;
        int mDividerHeight;
        int mDividerNumber = -1;
        int mLeftMargin = -1;

        public SimpleDividerDecoration(Context context, int colorId, int dividerHeight) {
            init(context, colorId, dividerHeight);
        }

        public SimpleDividerDecoration(Context context, int colorId, int dividerHeight, int dividerNumber) {
            init(context, colorId, dividerHeight);
            mDividerNumber = dividerNumber;
        }

        public SimpleDividerDecoration(Context context, int colorId, int dividerHeight, int dividerNumber, int leftMargin) {
            init(context, colorId, dividerHeight);
            mDividerNumber = dividerNumber;
            mLeftMargin = leftMargin;
        }

        private void init(Context context, int colorId, int dividerHeight) {
            mDividerPaint = new Paint();
            mDividerPaint.setColor(context.getResources().getColor(colorId));
            mDividerHeight = dividerHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mDividerHeight;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();
            int left;

            if (mLeftMargin > 0) {
                left = parent.getPaddingLeft() + mLeftMargin;
            } else {
                left = parent.getPaddingLeft();
            }

            int right = parent.getWidth() - parent.getPaddingRight();

            if (mDividerNumber > 0 && mDividerNumber <= childCount) {
                for (int i = 0; i < mDividerNumber; i++) {
                    View view = parent.getChildAt(i);
                    float top = view.getBottom();
                    float bottom = view.getBottom() + mDividerHeight;
                    c.drawRect(left, top, right, bottom, mDividerPaint);
                }

                return;
            }

            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + mDividerHeight;
                c.drawRect(left, top, right, bottom, mDividerPaint);
            }
        }
    }


}
