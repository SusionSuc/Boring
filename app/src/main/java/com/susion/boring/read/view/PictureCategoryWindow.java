package com.susion.boring.read.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.susion.boring.R;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.http.APIHelper;
import com.susion.boring.read.itemhandler.PictureCategoryIH;
import com.susion.boring.read.itemhandler.PictureColumnIH;
import com.susion.boring.read.mvp.entity.PictureCategoryResult;
import com.susion.boring.utils.PictureLoadHelper;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/3/26.
 */
public class PictureCategoryWindow {

    private Context mContext;
    FixedPopupWindow mWindow;

    private RecyclerView mRv;
    private List<Object> mData = new ArrayList<>();
    private View windowLayout;

    public PictureCategoryWindow(Context context) {
        mContext = context;
        mWindow = new FixedPopupWindow();
        init();
    }

    private void init() {
        windowLayout = LayoutInflater.from(mContext).inflate(R.layout.view_picture_category, null);
        mWindow.setContentView(windowLayout);
        mWindow.setWidth(UIUtils.getScreenWidth() / 3 * 2);
        mWindow.setHeight(UIUtils.getScreenHeight() / 4 * 3);
        mWindow.setBackgroundDrawable(mContext.getDrawable(R.drawable.bg_shape_rect_black));
        mWindow.setAnimationStyle(R.style.window_right_animation);
        mWindow.setFocusable(true);
        initView();
    }

    private void initView() {
        mRv = (RecyclerView) windowLayout.findViewById(R.id.list_view);
        mRv.setLayoutManager(RVUtils.getLayoutManager(mContext, LinearLayoutManager.VERTICAL));
        mRv.setAdapter(new BaseRVAdapter((Activity) mContext, mData) {

            private final int TYPE_PICTURE_COLUMN = 0;
            private final int TYPE_PICTURE_CATEGORY = 1;

            @Override
            protected void initHandlers() {
                registerItemHandler(TYPE_PICTURE_COLUMN, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new PictureColumnIH();
                    }
                });
                registerItemHandler(TYPE_PICTURE_CATEGORY, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new PictureCategoryIH();
                    }
                });
            }

            @Override
            protected int getViewType(int position) {

                Object o = getItem(position);

                if (o instanceof PictureCategoryResult.CategoryList.PictureCategory.Column) {
                    return TYPE_PICTURE_COLUMN;
                }

                if (o instanceof PictureCategoryResult.CategoryList.PictureCategory) {
                    return TYPE_PICTURE_CATEGORY;
                }

                return TYPE_PICTURE_COLUMN;
            }
        });
        Rect rect = new Rect(UIUtils.dp2Px(15), 0, UIUtils.dp2Px(15), 0);
        mRv.addItemDecoration(new RVUtils.NoLastDividerDecoration(mContext, R.color.white, 1, rect));

    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        mWindow.showAsDropDown(anchor, xoff, yoff, gravity);
        mData.clear();
        loadData();
    }

    public void dismiss() {
        mWindow.dismiss();
    }

    public boolean isShowing() {
        return mWindow.isShowing();
    }

    private void loadData() {
        APIHelper.getPictureService()
                .getPictureCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PictureCategoryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PictureCategoryResult res) {
                        if (res != null && res.getShowapi_res_body().getList() != null) {
                            List<PictureCategoryResult.CategoryList.PictureCategory> list = res.getShowapi_res_body().getList();
                            for (PictureCategoryResult.CategoryList.PictureCategory category : list) {
                                mData.add(category);
                                for (PictureCategoryResult.CategoryList.PictureCategory.Column column : category.getList()) {
                                    if (!PictureLoadHelper.mDiscardCategory.contains(column.getId())) {
                                        mData.add(column);
                                    }
                                }
                            }
                            mRv.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

}
