package com.susion.boring.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.ui.mainui.drawer.DividerMark;
import com.susion.boring.base.ui.OnLastItemVisibleListener;
import com.susion.boring.read.mvp.contract.ZhiHuDailyContract;

import java.util.List;

/**
 * Created by susion on 17/1/19.
 */
public class RVUtils {

    public static LinearLayoutManager getLayoutManager(Context context, int orientation) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(orientation);
        return manager;
    }

    public static StaggeredGridLayoutManager getStaggeredGridLayoutManager(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                }
            }
        };
        return staggeredGridLayoutManager;
    }

    public static RecyclerView.ItemDecoration getDrawerItemDecorationDivider(Context context, int color, Rect margin, List<DividerMark> data) {
        return new DrawerDividerDecoration(context, color, margin, data);
    }

    public static RecyclerView.ItemDecoration getZhiHuDailyNewsDecoration(Context context, int headerHeight, ZhiHuDailyContract.DailyNewsStickHeader dailyNewsStickHeader) {
        return new ZhiHuDailyNewsDecoration(context, dailyNewsStickHeader, headerHeight);
    }

    private static class DrawerDividerDecoration extends RecyclerView.ItemDecoration {
        private List<DividerMark> mData;
        private Rect mMargin;
        public Paint mDividerPaint;
        int mDividerHeight;

        public DrawerDividerDecoration(Context context, int color, Rect margin, List<DividerMark> data) {
            mDividerPaint = new Paint();
            mDividerPaint.setColor(context.getResources().getColor(color));
            mDividerHeight = 3;
            mData = data;
            mMargin = margin;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mDividerHeight;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();
            int left, right;

            left = mMargin.left > 0 ? parent.getPaddingLeft() + mMargin.left : parent.getPaddingLeft();
            right = mMargin.right > 0 ? parent.getWidth() - parent.getPaddingRight() - mMargin.right : parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < childCount; i++) {
                if (mData.get(i).needDivider) {
                    View view = parent.getChildAt(i);
                    float top = mMargin.top > 0 ? view.getBottom() + mMargin.top : view.getBottom();
                    float bottom = top + mDividerHeight;
                    c.drawRect(left, top, right, bottom, mDividerPaint);
                }
            }
        }
    }

    private static class ZhiHuDailyNewsDecoration extends RecyclerView.ItemDecoration {
        private ZhiHuDailyContract.DailyNewsStickHeader stickHeader;
        private TextPaint textPaint;
        private Paint paint;
        private int topGap;
        private Paint.FontMetrics fontMetrics;

        public ZhiHuDailyNewsDecoration(Context context, ZhiHuDailyContract.DailyNewsStickHeader dailyNewsStickHeader, int headerHeight) {
            Resources res = context.getResources();
            stickHeader = dailyNewsStickHeader;

            paint = new Paint();
            paint.setColor(res.getColor(R.color.colorAccent));

            textPaint = new TextPaint();
            textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            textPaint.setAntiAlias(true);
            textPaint.setTextSize(47);
            textPaint.setColor(Color.WHITE);
            textPaint.getFontMetrics(fontMetrics);
            textPaint.setTextAlign(Paint.Align.CENTER);
            fontMetrics = new Paint.FontMetrics();
            topGap = headerHeight;
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int pos = parent.getChildAdapterPosition(view);
            if (pos < 0) return;
            if (pos == 0 || isFirstInGroup(pos)) {//同组的第一个才添加padding
                outRect.top = topGap;
            } else {
                outRect.top = 0;
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int itemCount = state.getItemCount();
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            String preTitle, title = null;

            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(view);

                preTitle = title;
                title = stickHeader.getTitle(position);

                if (title.equals(preTitle)) continue;

                if (TextUtils.isEmpty(title) || !stickHeader.isShowTitle(position)) continue;

                float textY = Math.max(topGap, view.getTop());
                if (position + 1 < itemCount) {                 //下一个和当前不一样移动当前
                    String nextTitle = stickHeader.getTitle(position + 1);
                    int viewBottom = view.getBottom();
                    if (!nextTitle.equals(title) && viewBottom < textY) {      //组内最后一个view进入了header
                        textY = viewBottom;
                    }
                }
                paint.setColor(stickHeader.getHeaderColor(position));
                c.drawRect(left, textY - topGap, right, textY + UIUtils.dp2Px(5), paint);
                c.drawText(title, left + (view.getRight() - left) / 2, textY, textPaint);
            }
        }

        private boolean isFirstInGroup(int pos) {
            if (pos == 0) {
                return true;
            } else {
                String title = stickHeader.getTitle(pos - 1);
                String title2 = stickHeader.getTitle(pos);
                return !title.equals(title2);
            }
        }
    }

    /**
     * 添加到底部的监听.
     */
    public static void setOnLastItemVisibleListener(RecyclerView rv, final OnLastItemVisibleListener onLastItemVisibleListener) {

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && recyclerView.getAdapter() != null) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    if (layoutManager instanceof LinearLayoutManager) {
                        int lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                        if (lastVisiblePosition >= recyclerView.getAdapter().getItemCount() - 3) {
                            onLastItemVisibleListener.onLastItemVisible();
                        }
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {

                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        int last[] = new int[staggeredGridLayoutManager.getSpanCount()];
                        staggeredGridLayoutManager.findLastVisibleItemPositions(last);
                        for (int i = 0; i < last.length; i++) {
                            if (last[i] >= recyclerView.getAdapter().getItemCount() - 3) {
                                onLastItemVisibleListener.onLastItemVisible();
                                break;
                            }
                        }

                    } else if (layoutManager instanceof GridLayoutManager) {
                        GridLayoutManager manager = (GridLayoutManager) layoutManager;
                        int last[] = new int[manager.getSpanCount()];
                        if (last[0] >= recyclerView.getAdapter().getItemCount() - 3) {
                            onLastItemVisibleListener.onLastItemVisible();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public static class NoLastDividerDecoration extends RecyclerView.ItemDecoration {
        public Paint mDividerPaint;
        int mDividerHeight;
        Rect mMargin;

        public NoLastDividerDecoration(Context context, int colorId, int dividerHeight, Rect margin) {
            init(context, colorId, dividerHeight);
            mMargin = margin;
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
            int left, right;

            if (mMargin.left > 0) {
                left = parent.getPaddingLeft() + mMargin.left;
            } else {
                left = parent.getPaddingLeft();
            }

            if (mMargin.right > 0) {
                right = parent.getWidth() - parent.getPaddingRight() - mMargin.right;
            } else {
                right = parent.getWidth() - parent.getPaddingRight();
            }

            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + mDividerHeight;
                c.drawRect(left, top, right, bottom, mDividerPaint);
            }
        }
    }
}
